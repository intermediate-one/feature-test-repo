package com.example.testproject1

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testproject1.databinding.ActivityClipboardBinding


class ClipboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityClipboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClipboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 프래그먼트면 getSystemService 앞에 requireActivity() 추가.
        binding.btn3.setOnClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", binding.et3.text)
            clipboard.setPrimaryClip(clip)
        }
    }
}