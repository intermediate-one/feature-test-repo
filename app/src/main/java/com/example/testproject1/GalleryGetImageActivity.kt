package com.example.testproject1

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.testproject1.databinding.ActivityGalleryGetImageBinding

class GalleryGetImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGalleryGetImageBinding

    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) openGallery()
            else Log.e("myLogTag", "RequestPermission not granted")
        }

    // 가져온 사진 이미지뷰에 세팅
    private val pickImageLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) result.data?.data?.let {
                binding.iv2.setImageURI(it)
            } else Log.e("myLogTag", "result.resultCode != RESULT_OK")
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryGetImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn2.setOnClickListener {
            val permission = android.Manifest.permission.READ_EXTERNAL_STORAGE
            // TODO: 한번 권한 거부하면 다시 요청이 불가능한 문제. 버튼이 무반응이 된다.
            if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED
            ) requestPermissionLauncher.launch(permission)
            else openGallery()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        pickImageLauncher.launch(intent)
    }
}