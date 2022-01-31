package org.study2.notifydemo

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Icon
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
//        알림을 처리하는 앱을 시작
        val resultIntent = Intent(this, ResultActivity::class.java)
//        pendingIntent 는 인텐트를 다른 앱에 전달하고 전달받은 앱이 향후에 인텐트를 수행할수있게해준다.
        val pendingIntent = PendingIntent.getActivity(
            this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )
//        알림에 액션추가하기(액션은 알림메시지 밑에 버튼으로 나타나며 사용자가 탭하여 특정 인텐트 시작
        val icon: Icon = Icon.createWithResource(this, android.R.drawable.ic_dialog_info)
        val action: Notification.Action =
            Notification.Action.Builder(icon, "Open", pendingIntent).build()

        val notification = Notification.Builder(this@MainActivity, channelID)
            .setContentTitle("Example Notification")
            .setContentText("This is an example notification")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setChannelId(channelID)
//           알림이 두개이상일때 setNumber
            .setNumber(10)
//           setContentIntent 함수를 샤용해서 pendingIntent 객체를 알림 인스턴스에 지정하면된다
            .setContentIntent(pendingIntent)
            .setActions(action)
            .build()

        /*알림전송 알림이 전송될때는 알림 id가 저장된다 알림 id는 어떤정수값도가능하며
            향후에 해당 알림을 변경할때사용한다.*/
        notificationManager?.notify(notificationID, notification)
    }
}