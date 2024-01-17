package com.example.testproject1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testproject1.databinding.ActivityClipboardBinding

class ClipboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityClipboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClipboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}