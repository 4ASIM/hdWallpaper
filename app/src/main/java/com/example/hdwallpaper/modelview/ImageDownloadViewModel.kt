package com.example.hdwallpaper.modelview

import android.app.Application
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Environment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hdwallpaper.roomdb.AppDatabase
import com.example.hdwallpaper.roomdb.ImageEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ImageDownloadViewModel(application: Application) : AndroidViewModel(application) {

    val downloadStatus = MutableLiveData<String>()
    private val imageDao = AppDatabase.getDatabase(application).imageDao()

    fun saveImageToGallery(bitmap: Bitmap, userEmail: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val directory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "HDWallpapers")
                if (!directory.exists()) {
                    directory.mkdir()
                }

                val file = File(directory, "image_${System.currentTimeMillis()}.jpg")
                val outputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.flush()
                outputStream.close()

                MediaScannerConnection.scanFile(getApplication(), arrayOf(file.toString()), null, null)

                saveImagePathToRoomDB(file.path, userEmail)

                withContext(Dispatchers.Main) {
                    downloadStatus.postValue("Image saved to gallery")
                }

            } catch (e: IOException) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    downloadStatus.postValue("Failed to save image")
                }
            }
        }
    }

    private fun saveImagePathToRoomDB(imagePath: String, userEmail: String?) {
        userEmail?.let {
            viewModelScope.launch(Dispatchers.IO) {
                imageDao.insertImage(ImageEntity(imagePath = imagePath, userEmail = it))
            }
        }
    }
}
