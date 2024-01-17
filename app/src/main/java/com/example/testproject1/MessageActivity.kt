package com.example.testproject1

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testproject1.databinding.ActivityMessageBinding

class MessageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn1.setOnClickListener {
            val phoneNumber = binding.etPhone.text.toString()
            binding.tv1.text = phoneNumber  // for check

            val smsUri = Uri.parse("smsto:$phoneNumber") //phonNumber에는 01012345678과 같은 구성.
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = smsUri
            intent.putExtra("sms_body", "") //해당 값에 전달하고자 하는 문자메시지 전달
            startActivity(intent)
        }
    }
}