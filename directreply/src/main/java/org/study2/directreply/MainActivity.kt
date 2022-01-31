package org.study2.directreply

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Icon
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import org.study2.directreply.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
//    채널생성
    private var notificationManager : NotificationManager? = null
    private val channelID = "com.ebookfreazy.directreply.news"

    private  val notificationId = 101
    private val KEY_TEXT_REPLY = "key_text_reply"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(channelID,"DirectReply News","Example News Channel")

        handleIntent()
    }

  /*  getIntent() 함수를 호출하여 액티비티를 시작시킨 인텐트 객체의 참조를 얻는다. 그리고
    이것을 RemoteInput.getResultsFromIntent(intent) 함수인자로 전달하여 호출하면 응답텍스트를 갖는 Bundle 객체가
    반환되므로 이객체으 값을 추출하여 textview에 지정하면된다*/
    private fun handleIntent() {
        val intent = this.intent
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        if (remoteInput != null) {
            val inputString = remoteInput.getCharSequence(KEY_TEXT_REPLY).toString()
            binding.textView.text = inputString

//            전송할때 아이콘변경
            val repliedNotification = Notification.Builder(this,channelID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentText("Reply received")
                .build()
            notificationManager?.notify(notificationId,repliedNotification)
        }
    }

    private fun createNotificationChannel(id: String, name: String, description: String) {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(id,name,importance)

        channel.description = description
        channel.enableLights(true)
        channel.lightColor = Color.RED
        channel.enableVibration(true)
        channel.vibrationPattern = longArrayOf(100,200,300,400,500,400,300,200,400)

        notificationManager?.createNotificationChannel(channel)
    }
//    remoteInput 객체생성 및 카와 라벨 문자열로 초기화
    fun sendNotification(view: View) {
        val replyLabel = "Enter your reply here"
        val remoteInput = RemoteInput.Builder(KEY_TEXT_REPLY)
            .setLabel(replyLabel)
            .build()

//    PendingIntent 객체 생성하기
    val resultIntent =Intent(this,MainActivity::class.java)
    val resultPendingIntent =PendingIntent.getActivity(this,0,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT)

//    알림의 직접응답은 액션버튼을 통해서 할수있다 액션은 아이콘,버튼라벨,PendingIntent 객체,remoteInput 객체로구성괴고 생성되어야한다.
    val icon = Icon.createWithResource(this@MainActivity,android.R.drawable.ic_dialog_info)
    val replyAction = Notification.Action.Builder(icon,"Reply",resultPendingIntent)
        .addRemoteInput(remoteInput)
        .build()
//    알림생성 및 전송
    val newMessageNotification = Notification.Builder(this,channelID)
        .setColor(ContextCompat.getColor(this,R.color.design_default_color_primary))
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle("My Norification")
        .setContentText("This is a test message")
        .addAction(replyAction).build()

    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    notificationManager.notify(notificationId,newMessageNotification)
    }
}