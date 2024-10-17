package com.example.hdwallpaper.activity

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object VideoRetrofitInstance {
    private const val BASE_URL = "https://pixabay.com/"

    val api: VideoApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VideoApiService::class.java)
    }
}
