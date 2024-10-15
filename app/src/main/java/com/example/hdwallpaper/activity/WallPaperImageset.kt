package com.example.hdwallpaper.activity

import WallpaperViewModel
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.hdwallpaper.R
import com.example.hdwallpaper.databinding.ActivityWallPaperImagesetBinding

class WallPaperImageset : AppCompatActivity() {

    private lateinit var binding: ActivityWallPaperImagesetBinding
    private var isExpanded = false
    private val wallpaperViewModel: WallpaperViewModel by viewModels()

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWallPaperImagesetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imagePath = intent.getStringExtra("imagePath")

        if (imagePath != null) {
            Glide.with(this).load(imagePath).into(binding.imageView)
        } else {
            Toast.makeText(this, "Image not found", Toast.LENGTH_SHORT).show()
        }

        // Observe toast messages
        wallpaperViewModel.toastMessage.observe(this, Observer { message ->
            message?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })

        binding.mainFabBtn.setOnClickListener {
            if (isExpanded) shrinkFab() else expandFab()
        }

        binding.galleryFabBtn.setOnClickListener {
            if (imagePath != null) wallpaperViewModel.setWallpaper(imagePath)
        }

        binding.galleryTv.setOnClickListener {
            if (imagePath != null) wallpaperViewModel.setWallpaper(imagePath)
        }

        binding.shareFabBtn.setOnClickListener {
            if (imagePath != null) wallpaperViewModel.setLockScreenWallpaper(imagePath)
        }

        binding.shareTv.setOnClickListener {
            if (imagePath != null) wallpaperViewModel.setLockScreenWallpaper(imagePath)
        }

        binding.sendFabBtn.setOnClickListener {
            if (imagePath != null) wallpaperViewModel.setHomeScreenWallpaper(imagePath)
        }

        binding.sendTv.setOnClickListener {
            if (imagePath != null) wallpaperViewModel.setHomeScreenWallpaper(imagePath)
        }
    }

    private fun shrinkFab() {
        binding.mainFabBtn.startAnimation(rotateAntiClockWiseFabAnim)
        binding.galleryFabBtn.startAnimation(toBottomFabAnim)
        binding.shareFabBtn.startAnimation(toBottomFabAnim)
        binding.sendFabBtn.startAnimation(toBottomFabAnim)
        isExpanded = false
    }

    private fun expandFab() {
        binding.mainFabBtn.startAnimation(rotateClockWiseFabAnim)
        binding.galleryFabBtn.startAnimation(fromBottomFabAnim)
        binding.shareFabBtn.startAnimation(fromBottomFabAnim)
        binding.sendFabBtn.startAnimation(fromBottomFabAnim)
        isExpanded = true
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
