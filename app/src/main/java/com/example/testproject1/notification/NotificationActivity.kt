package com.example.testproject1.notification

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.testproject1.MainActivity
import com.example.testproject1.R
import com.example.testproject1.databinding.ActivityNotificationBinding


@Suppress("SpellCheckingInspection")
@SuppressLint("SetTextI18n")
class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 앱의 맨 처음에 실행해주는 것이 좋을 듯
        MyManagers.init(this)

        binding.btnNoti1.setOnClickListener {
            notification()
            binding.tvNoti.text = "btnNoti1 clicked"  //ddd
        }
        binding.btnNoti2.setOnClickListener {
            setAlarm(2)
            binding.tvNoti.text = "btnNoti2 clicked - setAlarm(2)"  //ddd
        }
        binding.btnNoti3.setOnClickListener {
            setAlarm(4)
            binding.tvNoti.text = "btnNoti3 clicked - setAlarm(4)"  //ddd
        }
        binding.btnNoti4.setOnClickListener {
            val sec = binding.etNoti.text.toString().toLongOrNull() ?: 0
            setAlarm(sec)
//            notification(sec)

            binding.tvNoti.text = "btnNoti4 clicked, sec = $sec"  //ddd
        }

        // TODO: 들어간 알람 지우는 기능?
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
        val builder = NotificationCompat.Builder(this, MyManagers.channelId).apply {
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

        MyManagers.notificationManager?.notify(11, builder.build())
            ?: Log.e("myTag", "노티 매니저 없음")
    }

//    // 시간 기본값 임의 지정
//    /** time format: "yyyy-MM-dd HH:mm:ss" */
//    private fun setAlarm(time: String = "2021-11-11 11:11:11") {
//        //AlarmReceiver에 값 전달
//        val receiverIntent = Intent(this, AlarmReceiver::class.java)
//        val pendingIntent = PendingIntent.getBroadcast(
//            this, 0, receiverIntent,
//            PendingIntent.FLAG_IMMUTABLE
//        )
//
//        //날짜 포맷을 바꿔주는 소스코드
//        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//        var datetime: Date? = null
//        try {
//            datetime = dateFormat.parse(time)
//        } catch (e: ParseException) {
//            e.printStackTrace()
//        }
//        val calendar = Calendar.getInstance()
//        datetime?.also { calendar.time = it }
//        MyNotificationManager.alarmManager?.also {
//            it[AlarmManager.RTC, calendar.timeInMillis] = pendingIntent
//        }
//            ?: Log.e("myTag", "알람 매니저 null")
//    }

    private fun setAlarm(sec: Long = 0) {
        val intent = Intent(this, MyReceiver::class.java).apply {
            // 일단 넣어봤다.
            putExtra("title", "알림 제목")
            putExtra("text", "리시버 인텐트 내용")
        }
        val pendingIntent = PendingIntent.getBroadcast(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        MyManagers.alarmManager?.also {
            it[AlarmManager.RTC, System.currentTimeMillis() + sec * 1000] = pendingIntent
        }
            ?: Log.e("myTag", "알람 매니저 null")

        val intent2 = Intent(this, MainActivity::class.java)
        val pendingIntent2 = PendingIntent.getActivity(
            this, 101, intent2,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val builder = NotificationCompat.Builder(this, MyManagers.channelId).apply {
            setSmallIcon(R.mipmap.ic_launcher)
            setWhen(System.currentTimeMillis())  // 이건 언제 보일지가 아니라 이 알림이 언제 알림인지 정보임
            setContentTitle("알림 타이틀")
            setContentText("저장한 노티. 꺼내와봅시다.")
            setContentIntent(pendingIntent2)
            setAutoCancel(true)
//            addAction(R.mipmap.ic_launcher, "Action", pendingIntent)
        }
        MyReceiver.savedNotification = builder.build()
    }
}