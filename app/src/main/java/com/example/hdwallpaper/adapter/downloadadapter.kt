package com.example.hdwallpaper.adapter

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.hdwallpaper.R
import com.example.hdwallpaper.dataclasses.dataclass
import com.example.hdwallpaper.dataclasses.downloaddataclass
import com.makeramen.roundedimageview.RoundedImageView

class downloadadapter(private val dataList: ArrayList<downloaddataclass>, private val context: Context) :
    RecyclerView.Adapter<downloadadapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.staggered_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val imageResId = dataList[position].imageURL

        if (imageResId != null) {
            // Load the image from resources using Glide
            Glide.with(context)
                .load(imageResId) // Now loading from resource ID
                .into(holder.staggeredImages)
        }

        val params = holder.staggeredImages.layoutParams
        when (position % 8) {
            0 -> params.height = 400
            1 -> params.height = 300
            2 -> params.height = 350
            3 -> params.height = 400
            4 -> params.height = 290
            5 -> params.height = 350
            6 -> params.height = 400
            7 -> params.height = 300
        }

        holder.staggeredImages.layoutParams = params

        // Set click listener for the image
        holder.staggeredImages.setOnClickListener {
            showPopupMenu(it, position)
        }
    }

    private fun showPopupMenu(view: View, position: Int) {
        // Create a PopupMenu
        val popupMenu = PopupMenu(context, view)
        val inflater: MenuInflater = popupMenu.menuInflater
        inflater.inflate(R.menu.image_options_menu, popupMenu.menu)
        popupMenu.show()

        // Set menu item click listener
        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.action_set_home_screen -> {
                    setWallpaperHomeScreen(dataList[position].imageURL)
                    true
                }
                R.id.action_set_lock_screen -> {
                    setWallpaperLockScreen(dataList[position].imageURL)
                    true
                }
                R.id.action_view_full_screen -> {
                    Toast.makeText(context, "View Full Screen selected", Toast.LENGTH_SHORT).show()
                    // Handle viewing image full screen here
                    true
                }
                else -> false
            }
        }
    }

    private fun setWallpaperHomeScreen(imageResId: Int?) {
        if (imageResId != null) {
            val wallpaperManager = WallpaperManager.getInstance(context)

            // Get screen dimensions for wallpaper
            val screenWidth = wallpaperManager.desiredMinimumWidth
            val screenHeight = wallpaperManager.desiredMinimumHeight

            // Use Glide to download the image resource and convert it to Bitmap
            Glide.with(context)
                .asBitmap()
                .load(imageResId) // Loading the resource ID as bitmap
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        try {
                            // Scale the bitmap to fit the screen dimensions
                            val scaledBitmap = Bitmap.createScaledBitmap(resource, screenWidth, screenHeight, true)

                            // Set the wallpaper with FLAG_SYSTEM for Home Screen
                            wallpaperManager.setBitmap(scaledBitmap, null, true, WallpaperManager.FLAG_SYSTEM)

                            Toast.makeText(context, "Wallpaper set to Home Screen", Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) {
                            Toast.makeText(context, "Failed to set wallpaper", Toast.LENGTH_SHORT).show()
                            e.printStackTrace()
                        }
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // Handle when the image is no longer needed
                    }
                })
        } else {
            Toast.makeText(context, "Invalid image resource", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setWallpaperLockScreen(imageResId: Int?) {
        if (imageResId != null) {
            val wallpaperManager = WallpaperManager.getInstance(context)

            // Get screen dimensions for wallpaper
            val screenWidth = wallpaperManager.desiredMinimumWidth
            val screenHeight = wallpaperManager.desiredMinimumHeight

            // Use Glide to download the image resource and convert it to Bitmap
            Glide.with(context)
                .asBitmap()
                .load(imageResId) // Loading the resource ID as bitmap
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        try {
                            // Scale the bitmap to fit the screen dimensions
                            val scaledBitmap = Bitmap.createScaledBitmap(resource, screenWidth, screenHeight, true)

                            // Set the wallpaper with FLAG_LOCK for Lock Screen
                            wallpaperManager.setBitmap(scaledBitmap, null, true, WallpaperManager.FLAG_LOCK)

                            Toast.makeText(context, "Wallpaper set to Lock Screen", Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) {
                            Toast.makeText(context, "Failed to set wallpaper", Toast.LENGTH_SHORT).show()
                            e.printStackTrace()
                        }
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // Handle when the image is no longer needed
                    }
                })
        } else {
            Toast.makeText(context, "Invalid image resource", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var staggeredImages: RoundedImageView = itemView.findViewById(R.id.staggeredImages)
    }
}
