package com.example.testproject1

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.testproject1.databinding.ActivityNotificationBinding

@Suppress("SpellCheckingInspection")
@SuppressLint("SetTextI18n")
class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding

    private val channelId = "one-channel"
    private lateinit var manager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNotificationManager()

        binding.btnNoti1.setOnClickListener {
            binding.tvNoti.text = "btnNoti1 clicked"  //ddd
            notification()
        }
        binding.btnNoti4.setOnClickListener {
            val sec = binding.etNoti.text.toString().toIntOrNull() ?: 0
            notification(sec)

            binding.tvNoti.text = "btnNoti4 clicked, sec = $sec"  //ddd
        }
    }

    private fun initNotificationManager() {
        manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // 안드 8.0 Oreo 이상부터는 알림 채널을 먼저 만들어야 한다
        manager.createNotificationChannel(
            NotificationChannel(
                channelId, "My Channel One", NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "My Channel One Description"
                setShowBadge(true)
                val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)!!
                val audioAttributes = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()
                setSound(uri, audioAttributes)
                enableVibration(true)
            })
    }

    private fun notification(sec: Int = 0) {
        // 알림에 띄울 이미지 비트맵이랑 실행시킬 인텐트 준비.
        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // 알림 정보
        val builder = NotificationCompat.Builder(this, channelId).apply {
            setSmallIcon(R.mipmap.ic_launcher)
            setWhen(System.currentTimeMillis())  // 이건 언제 보일지가 아니라 이 알림이 언제 알림인지 정보임
            setContentTitle("알림 타이틀")
            setContentText("알림 텍스트. 여기가 내용~")
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
            setLargeIcon(bitmap)
            setContentIntent(pendingIntent)
            setAutoCancel(true)
//            addAction(R.mipmap.ic_launcher, "Action", pendingIntent)
        }

        manager.notify(11, builder.build())
    }
}