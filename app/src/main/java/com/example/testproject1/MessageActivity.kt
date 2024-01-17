package com.example.testproject1

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
            binding.tv1.text = binding.etPhone.text.toString()
        }
    }
}