package com.example.hdwallpaper.modelview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.hdwallpaper.activity.RetrofitInstance
import com.example.hdwallpaper.dataclasses.dataclass
import com.example.hdwallpaper.dataclasses.pixbayclass

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    val dataList = MutableLiveData<ArrayList<dataclass>>()
    val message = MutableLiveData<String>()


    fun fetchImages() {
        val apiService = RetrofitInstance.api
        apiService.getImages("46503684-bc100fdba61e2f74c88248b50")
            .enqueue(object : Callback<pixbayclass> {
                override fun onResponse(call: Call<pixbayclass>, response: Response<pixbayclass>) {
                    if (response.isSuccessful && response.body() != null) {
                        val hits = response.body()!!.hits
                        val images = ArrayList<dataclass>()
                        for (hit in hits) {
                            images.add(dataclass(hit.webformatURL, "Sample Image"))
                        }
                        dataList.postValue(images)
                    } else {
                        message.postValue("Failed to load images")
                    }
                }

                override fun onFailure(call: Call<pixbayclass>, t: Throwable) {
                    message.postValue("Error: ${t.message}")
                }
            })
    }
}
