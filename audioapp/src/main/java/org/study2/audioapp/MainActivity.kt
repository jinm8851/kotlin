package org.study2.audioapp

import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import org.study2.audioapp.databinding.ActivityMainBinding
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var mediaRecorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null
    private var audioFilePath: String? = null
    private var isRecording = false

    private val RECORD_REQUEST_CODE = 101
    private val STORAGE_REQUEST_CODE = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        audioSetup()
    }

    private fun audioSetup() {
        if (!hasMicrophone()) {
            binding.stopButton.isEnabled = false
            binding.playButton.isEnabled = false
            binding.recordButton.isEnabled = false
        } else {
            binding.playButton.isEnabled = false
            binding.stopButton.isEnabled = false
        }
        /*이코드에서는 장치의 외부 저장소 (많은 안드로이드 장치가 실제 장치없이 sd카드를 내부적으로 구현하는 경우가 많지만
                그렇더라도 외부 저장소라고 한다) 의 위치를 확인하고 이 위치를 사용해서 녹음된 오디오가 저장될 myaudio.3gp
                파일의 경로를 생성한다 외부저장소(sd카드)의 경로는 필요한 저장소 타입을 인자로 전달하여 getExternalFilesDir()
        함수를 호출하면 얻을수 있다(여기서는 음악디렉터리에 대한 경로를 요청한다 (Environment.DIRECTORY_MUSIC))
        (절대경로 absolutePath 시스템의 루트(/)부터 시작하는 경로) */
        audioFilePath =
            this.getExternalFilesDir(Environment.DIRECTORY_MUSIC)?.absolutePath + "/myaudio.3gp"

//        퍼미션요청
        requestPermission(android.Manifest.permission.RECORD_AUDIO, RECORD_REQUEST_CODE)
    }

    /*이함수에서는 play stop 버튼을 비활성화해야 한다 그리고 MediaRecorder 인스턴스가 다음의 정보를 갖도록 구성한다
    * 오디오소스, 출력포멧, 잍코딩포맷, 오디오 데이터가 저장될 파일의 위치다. 끝으로 mediaRecorder 객체의 prepare()와
    *  start() 함수를 호출해야 한다*/
    fun recordAudio(view: View) {
        isRecording = true
        binding.stopButton.isEnabled = true
        binding.playButton.isEnabled = false
        binding.recordButton.isEnabled = false

        try {
            mediaRecorder = MediaRecorder()
            mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mediaRecorder?.setOutputFile(audioFilePath)
            mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            mediaRecorder?.prepare()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mediaRecorder?.start()
    }

    /*play 버튼은 활성화하고 stop 버튼은 비활성화 한다 그런다음 mediaRecorder
    * 인스턴스를 재설정해야 한다*/
    fun stopAudio(view: View) {
        binding.stopButton.isEnabled = false
        binding.playButton.isEnabled = true

        if (isRecording) {
            binding.recordButton.isEnabled = false
            mediaRecorder?.stop()
            mediaRecorder?.release()
            mediaRecorder = null
            isRecording = false
        } else {
            mediaPlayer?.release()
            mediaPlayer = null
            binding.recordButton.isEnabled = true
        }
    }

    /*새로운 MediaPlayer 인스턴스를 생성하고 외부 저장소 (sd카드)에 위치한
    오디오 파일을 데이터 소스로 지정한 후 재생을 준비하고 시작한다.*/
    fun playAudio(view: View) {
        binding.playButton.isEnabled = false
        binding.recordButton.isEnabled = false
        binding.stopButton.isEnabled = true

        mediaPlayer = MediaPlayer()
        mediaPlayer?.setDataSource(audioFilePath)
        mediaPlayer?.prepare()
        mediaPlayer?.start()
    }

    //    마이크유무확인
    private fun hasMicrophone(): Boolean {
        val pmanager = this.packageManager
        return pmanager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE)
    }

    //   Permission 추가
    private fun requestPermission(permissionType: String, requestCode: Int) {
        val permission = ContextCompat.checkSelfPermission(this, permissionType)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(permissionType), requestCode)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            RECORD_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    binding.recordButton.isEnabled = false

                    Toast.makeText(this, "Record permission required", Toast.LENGTH_LONG)
                        .show()
                } else {
                    requestPermission(
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        STORAGE_REQUEST_CODE
                    )
                }
                return
            }
            STORAGE_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    binding.recordButton.isEnabled = false
                    Toast.makeText(
                        this, "External Storage permission required",
                        Toast.LENGTH_LONG
                    ).show()
                }
                return
            }
        }
    }
}