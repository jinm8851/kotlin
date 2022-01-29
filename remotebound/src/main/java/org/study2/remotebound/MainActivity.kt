package org.study2.remotebound

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.view.View
import java.lang.Exception


/*로컬바운드서비스처럼 클라이언트에서 ServiceConnection 클래스의 인스턴스구현해야함
또한 이 클래스의 onServiceConnected 와 onServiceDisconnected 구현해야함*/

class MainActivity : AppCompatActivity() {

    var myService: Messenger? = null
    var isBound: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        인텐트와 ServiceConnection 인스턴스의 참조를 인자로 전달하여 bindService() 함수호출
        var intent = Intent(applicationContext, RemoteService::class.java)
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE)

        /*        서비스가연결되었는지 확인후 서비스에보여줄 문자열을 Bundle 객체에 추가하고
                    Message 객체에 추가한후 서비스로 전송한다*/
    }
        fun sendMessage(view: View) {
            if (!isBound) return
        val msg = Message.obtain()
            val bundle = Bundle()

            bundle.putString("myString","Message Received")

            msg.data =bundle

            try {
                myService?.send(msg)
            }catch (e:Exception){e.printStackTrace()}
        }

    //    ServiceConnection 크래스추가 ,메신저참조 변수추가
    private val myConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            myService = Messenger(p1)
            isBound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            myService = null
            isBound = false
        }
    }
}