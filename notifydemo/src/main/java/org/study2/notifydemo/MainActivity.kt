package org.study2.notifydemo

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    //    notificationManager 인스턴스 생성및초기화
    private var notificationManager: NotificationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChanel(
            "com.ebookfrenzy.notifydemo.news",
            "NotifyDemo News",
            "Exmaple News Channerl"
        )
    }

    private fun createNotificationChanel(id: String, name: String, description: String) {
//        알림중요도(high,low,max,min,none)
        val importance = NotificationManager.IMPORTANCE_LOW
//        channel 생성
        val channel = NotificationChannel(id, name, importance)

        channel.description = description
        channel.enableLights(true)
        channel.lightColor = Color.RED
        channel.enableVibration(true)
        channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)

        notificationManager?.createNotificationChannel(channel)
       /* 알림채널삭제
        val channelID = "com.ebookfrenzy.notifydemo.news"
        notificationManager?.deleteNotificationChannel(channelID)*/

    }

    //    Notification.Builder 클래스를 사용해 알림을 생성하며 이때 알림의 아이콘,제목,내용을 포함한다
    fun sendNotification(view: View) {
        val notificationID = 101

        val channelID = "com.ebookfrenzy.notifydemo.news"

        val notification = Notification.Builder(this@MainActivity, channelID)
            .setContentTitle("Example Notification")
            .setContentText("This is an example notification")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setChannelId(channelID)
//           알림이 두개이상일때 setNumber
            .setNumber(10)
            .build()

    /*알림전송 알림이 전송될때는 알림 id가 저장된다 알림 id는 어떤정수값도가능하며
        향후에 해당 알림을 변경할때사용한다.*/
        notificationManager?.notify(notificationID, notification)
    }
}