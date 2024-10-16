package com.example.hdwallpaper.modelview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.hdwallpaper.activity.VideoRetrofitInstance
import com.example.hdwallpaper.dataclasses.VideoHit
import com.example.hdwallpaper.dataclasses.VideoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideoViewModel(application: Application) : AndroidViewModel(application) {
    val videoList = MutableLiveData<ArrayList<VideoHit>>()
    val message = MutableLiveData<String>()

    fun fetchVideos() {
        val apiService = VideoRetrofitInstance.api
        apiService.getVideos("46503684-bc100fdba61e2f74c88248b50")  //,"transportation"
            .enqueue(object : Callback<VideoResponse> {
                override fun onResponse(call: Call<VideoResponse>, response: Response<VideoResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        videoList.postValue(ArrayList(response.body()!!.hits))
                    } else {
                        message.postValue("Failed to load videos")
                    }
                }

                override fun onFailure(call: Call<VideoResponse>, t: Throwable) {
                    message.postValue("Error: ${t.message}")
                }
            })
    }
}
