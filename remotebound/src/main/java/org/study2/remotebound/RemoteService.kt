package org.study2.remotebound

import android.app.Service
import android.content.Intent
import android.os.*
import android.widget.Toast

class RemoteService : Service() {

    private val myMessenger = Messenger(IncomingHandler())

//    클라이언트로부터 메세지가 수시될때 호출되며 클라이언트가 서비스에 전달할 필요가있는 데이터를
//    포함하는 Message 객체를 인자로 받는다 (헨들러구현)
    inner class IncomingHandler : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            val data = msg.data
            val dataString = data.getString("myString")
            Toast.makeText(applicationContext,dataString,Toast.LENGTH_LONG).show()
        }
    }
//핸들러클래스이 인스턴스를 생성하고 이것을 Messenger 클래스이 생성자로 전달 새로운 객체를 Messenger 객체생성
    override fun onBind(intent: Intent): IBinder {
        return myMessenger.binder
    }
}