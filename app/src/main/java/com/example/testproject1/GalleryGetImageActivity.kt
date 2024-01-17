package com.example.testproject1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testproject1.databinding.ActivityGalleryGetImageBinding

class GalleryGetImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGalleryGetImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryGetImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}