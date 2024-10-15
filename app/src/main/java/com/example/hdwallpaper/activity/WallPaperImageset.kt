package com.example.hdwallpaper.activity

import android.app.WallpaperManager
import android.graphics.BitmapFactory
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.hdwallpaper.R
import com.example.hdwallpaper.databinding.ActivityWallPaperImagesetBinding
import java.io.IOException

class WallPaperImageset : AppCompatActivity() {

    private lateinit var binding: ActivityWallPaperImagesetBinding
    private var isExpanded = false

    private val fromBottomFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.from_bottom_fab)
    }
    private val toBottomFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.to_bottom_fab)
    }
    private val rotateClockWiseFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.rotate_clock_wise)
    }
    private val rotateAntiClockWiseFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.rotate_anti_clock_wise)
    }
    private val fromBottomBgAnim: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim)
    }
    private val toBottomBgAnim: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWallPaperImagesetBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val imagePath = intent.getStringExtra("imagePath")

        if (imagePath != null) {
            Glide.with(this)
                .load(imagePath)
                .into(binding.imageView) // Assuming imageView is the ImageView for displaying the image
        } else {
            Toast.makeText(this, "Image not found", Toast.LENGTH_SHORT).show()
        }

        binding.mainFabBtn.setOnClickListener {

            if (isExpanded) {
                shrinkFab()
            } else {
                expandFab()
            }

        }


        binding.galleryFabBtn.setOnClickListener {
            setWallpaper(imagePath)
        }

        binding.galleryTv.setOnClickListener {
            setWallpaper(imagePath)
        }
        binding.shareFabBtn.setOnClickListener {
            setWallpaperss(imagePath)
        }

        binding.shareTv.setOnClickListener {
            setWallpaperss(imagePath)
        }

        binding.sendFabBtn.setOnClickListener {
            setHomeScreenWallpaper(imagePath)
        }

        binding.sendTv.setOnClickListener {
            setHomeScreenWallpaper(imagePath)
        }

    }

    private fun setHomeScreenWallpaper(imagePath: String?) {
        if (imagePath != null) {
            try {
                val wallpaperManager = WallpaperManager.getInstance(this)
                val bitmap = BitmapFactory.decodeFile(imagePath)

                wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)
                Toast.makeText(this, "Home screen wallpaper set successfully!", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Failed to set home screen wallpaper", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "No image available", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setWallpaper(imagePath: String?) {
        if (imagePath != null) {
            try {
                val wallpaperManager = WallpaperManager.getInstance(this)
                val bitmap = BitmapFactory.decodeFile(imagePath)
                wallpaperManager.setBitmap(bitmap)
                Toast.makeText(this, "Wallpaper set successfully!", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Failed to set wallpaper", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "No image available", Toast.LENGTH_SHORT).show()
        }
    }
    private fun setWallpaperss(imagePath: String?) {
        if (imagePath != null) {
            try {
                val wallpaperManager = WallpaperManager.getInstance(this)
                val bitmap = BitmapFactory.decodeFile(imagePath)

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    // Set wallpaper only for lock screen
                    wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
                    Toast.makeText(this, "Lock screen wallpaper set successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Lock screen wallpaper can only be set on Android N (API 24) and above.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Failed to set lock screen wallpaper", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "No image available", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onGalleryClicked() {
        Toast.makeText(this, "Gallery Clicked", Toast.LENGTH_SHORT).show()
    }

    private fun shrinkFab() {


        binding.mainFabBtn.startAnimation(rotateAntiClockWiseFabAnim)
        binding.galleryFabBtn.startAnimation(toBottomFabAnim)
        binding.shareFabBtn.startAnimation(toBottomFabAnim)
        binding.sendFabBtn.startAnimation(toBottomFabAnim)
        binding.galleryTv.startAnimation(toBottomFabAnim)
        binding.shareTv.startAnimation(toBottomFabAnim)
        binding.sendTv.startAnimation(toBottomFabAnim)



        isExpanded = !isExpanded
    }

    private fun expandFab() {


        binding.mainFabBtn.startAnimation(rotateClockWiseFabAnim)
        binding.galleryFabBtn.startAnimation(fromBottomFabAnim)
        binding.shareFabBtn.startAnimation(fromBottomFabAnim)
        binding.sendFabBtn.startAnimation(fromBottomFabAnim)
        binding.galleryTv.startAnimation(fromBottomFabAnim)
        binding.shareTv.startAnimation(fromBottomFabAnim)
        binding.sendTv.startAnimation(fromBottomFabAnim)


        isExpanded = !isExpanded
    }

    override fun onBackPressed() {

        if (isExpanded) {
            shrinkFab()
        } else {
            super.onBackPressed()

        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {

        if (ev?.action == MotionEvent.ACTION_DOWN) {

            if (isExpanded) {
                val outRect = Rect()
                binding.fabConstraint.getGlobalVisibleRect(outRect)
                if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    shrinkFab()
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

}