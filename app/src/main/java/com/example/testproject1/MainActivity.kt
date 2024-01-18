package com.example.testproject1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.example.testproject1.databinding.ActivityMainBinding
import com.example.testproject1.notification.NotificationActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.commit {
            replace(binding.flMain.id, ClipboardFragment())
            setReorderingAllowed(true)
            addToBackStack("")
        }

        val intent = Intent(this, NotificationActivity::class.java)
        startActivity(intent)
    }
}