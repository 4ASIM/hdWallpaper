package com.example.hdwallpaper

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hdwallpaper.databinding.ActivityVideoDownloadBinding

class VideoDownloadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoDownloadBinding
    private var videoUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVideoDownloadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        videoUrl = intent.getStringExtra("VIDEO_URL")
        Toast.makeText(this, videoUrl, Toast.LENGTH_LONG).show()

        if (videoUrl.isNullOrEmpty()) {
            Toast.makeText(this, "Invalid video URL", Toast.LENGTH_SHORT).show()
            return
        }

        binding.VideoView.setVideoURI(Uri.parse(videoUrl))
        binding.VideoView.start()
        binding.VideoView.setOnErrorListener { _, what, extra ->
            true
        }

        binding.fab.setOnClickListener {
            downloadVideo(videoUrl!!)
        }
    }

    private fun downloadVideo(videoUrl: String) {
        try {
            val request = DownloadManager.Request(Uri.parse(videoUrl))
            request.setTitle("Downloading Video")
            request.setDescription("Downloading video from Pixabay")
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "HdWallpaper.mp4"
            )

            val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            downloadManager.enqueue(request)

            Toast.makeText(this, "Video download started", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to download video: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
