package com.example.hdwallpaper.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.hdwallpaper.R

class imageselection : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imageselection)

        val imageView = findViewById<ImageView>(R.id.fullImageView)
        val imageUrl = intent.getStringExtra("IMAGE_RES_ID")

        if (imageUrl != null) {
            Glide.with(this).load(imageUrl).into(imageView)
        }
    }
}
