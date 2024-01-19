package com.example.testproject1.notification

import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.testproject1.MainActivity
import com.example.testproject1.R


class MyReceiver : BroadcastReceiver() {
    // TODO: 앱 종료하면 이것도 날아가고 매니저도 날아감
    companion object {
        var savedNotification: Notification? = null
    }

    override fun onReceive(context: Context, intent: Intent) {
        // 알림창 클릭 시 실행할 액티비티
        val intent2 = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 101, intent2,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, MyManagers.channelId).apply {
            setSmallIcon(R.mipmap.ic_launcher)
            setWhen(System.currentTimeMillis())  // 이건 언제 보일지가 아니라 이 알림이 언제 알림인지 정보임
            setContentTitle("알림 타이틀")
            setContentText("알림 텍스트. 알람 리시버의 onReceive")
//            flag = !flag  // 알림 버튼 클릭 시 번갈아 스타일 보려고 만든 flag
//            if (flag) {
//                setStyle(
//                    NotificationCompat.BigTextStyle()
//                        .bigText("setStyle(NotificationCompat.BigTextStyle().bigText(지금내용)  아주 긴 텍스트를 쓸 때는 여기에 하면 된다. 아주 긴 텍스트 아주 긴 텍스트 아주 긴 텍스트 아주 긴 텍스트 아주 긴 텍스트 아주 긴 텍스트 끝")
//                )
//            } else {
//                setStyle(
//                    NotificationCompat.BigPictureStyle()
//                        .bigPicture(bitmap)
//                        .bigLargeIcon(null)  // hide largeIcon while expanding
//                )
//            }
//            setLargeIcon(bitmap)
            setContentIntent(pendingIntent)
            setAutoCancel(true)
//            addAction(R.mipmap.ic_launcher, "Action", pendingIntent)
        }

        // TODO: 알람 설정해두고 앱 종료하면, 노티 매니저, 세이브드노티 다 날아간다.
        if (savedNotification != null) {
            MyManagers.notificationManager?.notify(11, savedNotification)
                ?: Log.e("myTag", "노티 매니저 없음 (세이브드노티 o)")
        } else {
            MyManagers.notificationManager?.notify(11, builder.build())
                ?: Log.e("myTag", "노티 매니저 없음 (세이브드노티 x)")
        }

    }
}