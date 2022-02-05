package org.study2.videoplayer

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.MediaController
import org.study2.videoplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private var mediaController: MediaController? = null

    private var TAG = "Video Player"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        재생될 동영상의 경로를 구성 후 재생
        configureVideoView()
    }

    private fun configureVideoView() {
        binding.videoView1.setVideoURI(Uri.parse("android.resource://"
        +getPackageName() + "/" + R.raw.movie))

//        MediaController 추가
        mediaController = MediaController(this)
        mediaController?.setAnchorView(binding.videoView1)
        binding.videoView1.setMediaController(mediaController)

//        동영상재생시간출력 (로그창) 동영상반복재생
        binding.videoView1.setOnPreparedListener{
            it.isLooping = true
            Log.i(TAG,"Duration = " + binding.videoView1.duration)
        }
        binding.videoView1.start()
    }
}