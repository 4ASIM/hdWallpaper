package com.example.hdwallpaper.adapter
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hdwallpaper.R
import com.example.hdwallpaper.VideoDownloadActivity

import com.example.hdwallpaper.dataclasses.VideoHit
import com.makeramen.roundedimageview.RoundedImageView

class VideoAdapter(private val videoList: ArrayList<VideoHit>, private val context: Context) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.video_item, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = videoList[position]
        Glide.with(context)
            .load(video.videos.medium.thumbnail)
            .placeholder(R.drawable.placeholder_image)
            .into(holder.videoThumbnail)
        val params = holder.videoThumbnail.layoutParams
        when (position % 8) {
            0 -> params.height = 500
            1 -> params.height = 500
            2 -> params.height = 500
            3 -> params.height = 500
            4 -> params.height = 500
            5 -> params.height = 500
            6 -> params.height = 500
            7 -> params.height = 500
        }

        holder.videoThumbnail.layoutParams = params
        holder.videoThumbnail.setOnClickListener {
            val intent = Intent(context, VideoDownloadActivity::class.java)
            intent.putExtra("VIDEO_URL", video.videos.medium.url)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = videoList.size

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var videoThumbnail: RoundedImageView = itemView.findViewById(R.id.videoThumbnail)
    }
}
