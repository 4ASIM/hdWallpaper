package com.example.hdwallpaper.dataclasses

data class VideoResponse(
    val hits: List<VideoHit>,
    val total: Int,
    val totalHits: Int
)

data class VideoHit(
    val id: Int,
    val pageURL: String,
    val videos: VideoDetails,

)

data class VideoDetails(
    val large: VideoSize,
    val medium: VideoSize,
    val small: VideoSize,
    val tiny: VideoSize
)

data class VideoSize(
    val url: String,
    val width: Int,
    val height: Int,
    val thumbnail: String,
    val size: Int
)
