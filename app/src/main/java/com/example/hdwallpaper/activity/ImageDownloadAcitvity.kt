package com.example.hdwallpaper.activity

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.hdwallpaper.databinding.ActivityImageDownloadAcitvityBinding
import com.example.hdwallpaper.modelview.ImageDownloadViewModel
import com.google.firebase.auth.FirebaseAuth

class ImageDownloadAcitvity : AppCompatActivity() {

    private lateinit var binding: ActivityImageDownloadAcitvityBinding
    private var imageBitmap: Bitmap? = null
    private val imageDownloadViewModel: ImageDownloadViewModel by viewModels()
    val userEmail = FirebaseAuth.getInstance().currentUser?.email

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityImageDownloadAcitvityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUrl = intent.getStringExtra("IMAGE_URL")


        imageUrl?.let {
            Glide.with(this).asBitmap().load(it).into(binding.imageView)
        }

        imageDownloadViewModel.downloadStatus.observe(this, Observer { status ->
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show()
        })

        binding.fab.setOnClickListener {
            binding.imageView.isDrawingCacheEnabled = true
            binding.imageView.buildDrawingCache()
            imageBitmap = Bitmap.createBitmap(binding.imageView.drawingCache)
            binding.imageView.isDrawingCacheEnabled = false

            if (checkStoragePermissions()) {

                imageDownloadViewModel.saveImageToGallery(imageBitmap!!, userEmail)
            } else {
                requestStoragePermissions()
            }
        }
    }

    private fun checkStoragePermissions(): Boolean {
        val result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestStoragePermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1001
    }
}
