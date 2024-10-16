// VideoApiService.kt
package com.example.hdwallpaper.activity

import com.example.hdwallpaper.dataclasses.VideoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoApiService {
    @GET("/api/videos/")
    fun getVideos(
        @Query("key") apiKey: String,
//        @Query("category") category: String,
        @Query("per_page") perPage: Int = 50
    ): Call<VideoResponse>
}
