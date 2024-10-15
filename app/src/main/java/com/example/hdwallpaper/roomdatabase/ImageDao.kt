package com.example.hdwallpaper.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ImageDao {

    @Insert
     fun insertImage(imageEntity: ImageEntity)

    @Query("SELECT * FROM images WHERE userEmail = :email")
    fun getImagesByEmail(email: String): LiveData<List<ImageEntity>>

    @Delete
    fun deleteImage(imageEntity: ImageEntity)

}
