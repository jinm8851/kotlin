package org.study2.localbound

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import java.text.SimpleDateFormat
import java.util.*

class BoundService : Service() {

    private val myBinder = MyLocalBinder()

    override fun onBind(intent: Intent): IBinder {
        return myBinder
    }

//    시간을 반환한는 함수
    fun getCurrentTime() : String {
        val dateformat = SimpleDateFormat("HH:mm:ss MM/dd/yyyy", Locale.US)
        return dateformat.format(Date())
    }

//    바운드 서비스클레스에서 Binder()서브클레스를 생성후 클라이언트가 호출할수있는 하나이상의 사료운 함수추가
    inner class MyLocalBinder : Binder() {
        fun getService() : BoundService {
            return this@BoundService
        }
    }
}