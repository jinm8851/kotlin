package org.study2.permissiondemo

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val TAG = "PermissionDemo"
    private val RECODE_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupPermissions()
    }

    private fun setupPermissions() {

//        현재 엑티비티의 참조와 요청한는 퍼미션을 인자로 전달한다
        val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
//        PackageManager.PERMISSION_GRANTED 또는 PackageManager.PERMISSION_DENIED를 반환 체크한다
        if (permission != PackageManager.PERMISSION_GRANTED ) {
            Log.i(TAG,"Permission to record denied")

            /*사용자가 퍼미션을 거절했을때 설명을 보여주는 코드
            ActivityCompat.shouldShowRequestPermissionRationale() 퍼미션을거절한 적이 있으면 true를 반환한다 없으면 false
            true가 반환되는 경우 앱에서는 퍼미션이 필요한 이유를 포함하는 대화상자를 보여준다*/
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.RECORD_AUDIO)) {
                val builder = AlertDialog.Builder(this)
                builder.setMessage(
                    "Permission to access the microphone is required for this app to record audio.")
                    .setTitle("Permission required")
//OK 버튼을 눌렀을경우 makeRequest() 함수 퍼미션요청코드 호출
                builder.setPositiveButton("OK") { dialog, id ->
                    Log.i(TAG,"Clicked")

            makeRequest()
                }
                val dialog = builder.create()
                dialog.show()
            }else {
                makeRequest()
            }
        }
    }
/*런타임시 퍼미션 요청은 ActivityCompat 클래스의 requestPermissions() 함수를 호출하여 처리한다
   requestPermissions() 함수는 세 개의 인자를 받는다 현재 엑티비티 참조, 요청퍼미션의 식별자, 요청코드
    작업이 끝나면 onRequestPermissionsResult() 함수가 호출된다*/
    private fun makeRequest() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO),RECODE_REQUEST_CODE)
    }
// 작업이 끝나면 onRequestPermissionsResult() 함수가 호출된다
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode) {
            RECODE_REQUEST_CODE -> {
                if (grantResults.isEmpty()|| grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Log.i(TAG,"Permission has been denied by user")
                }else {
                    Log.i(TAG,"Permission has been granted by user")
                }
            }
        }
    }
}