package com.example.hdwallpaper.activity

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.hdwallpaper.R
import com.example.hdwallpaper.modelview.ImageDownloadViewModel

class ImageDownloadAcitvity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var downloadButton: Button
    private var imageUrl: String? = null
    private var imageBitmap: Bitmap? = null // Hold the bitmap here

    // Use ViewModel delegation
    private val imageDownloadViewModel: ImageDownloadViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_download_acitvity)

        imageView = findViewById(R.id.imageView)
        downloadButton = findViewById(R.id.downloadButton)

        // Get the image URL from the intent
        imageUrl = intent.getStringExtra("IMAGE_URL")

        // Load the image
        imageUrl?.let {
            Glide.with(this).asBitmap().load(it).into(imageView)
        }

        // Observe download status LiveData from ViewModel
        imageDownloadViewModel.downloadStatus.observe(this, Observer { status ->
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show()
        })

        // Set the download button to save the image from imageView
        downloadButton.setOnClickListener {
            imageView.isDrawingCacheEnabled = true
            imageView.buildDrawingCache()
            imageBitmap = Bitmap.createBitmap(imageView.drawingCache)
            imageView.isDrawingCacheEnabled = false

            if (checkStoragePermissions()) {
                // Save the image bitmap to the gallery
                imageDownloadViewModel.saveImageToGallery(imageBitmap!!, "user@example.com") // replace with actual user email
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
