package com.example.hdwallpaper.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hdwallpaper.R
import com.example.hdwallpaper.activity.WallPaperImageset
import com.example.hdwallpaper.fragment.DownloadFragment
import com.example.hdwallpaper.roomdb.ImageDao
import com.example.hdwallpaper.roomdb.ImageEntity
import com.makeramen.roundedimageview.RoundedImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class downloadadapter(private val dataList: MutableList<ImageEntity>, private val context: Context, private val imageDao: ImageDao
):

    RecyclerView.Adapter<downloadadapter.MyViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.staggered_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val imageEntity = dataList[position]
        val imagePath = imageEntity.imagePath

        if (imagePath != null) {
            Glide.with(context)
                .load(imagePath)
                .into(holder.staggeredImages)
        }


        val params = holder.staggeredImages.layoutParams
        when (position % 8) {
            0 -> params.height = 450
            1 -> params.height = 350
            2 -> params.height = 400
            3 -> params.height = 450
            4 -> params.height = 330
            5 -> params.height = 400
            6 -> params.height = 450
            7 -> params.height = 350
        }
        holder.staggeredImages.layoutParams = params


        holder.staggeredImages.setOnClickListener { view ->
            showPopupMenu(view, imageEntity)
        }
    }

    private fun showPopupMenu(view: View, imageEntity: ImageEntity) {
        val popupMenu = PopupMenu(context, view)
        popupMenu.menuInflater.inflate(R.menu.image_options_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_set_home_screen -> {
                    // Handle view image
                    val intent = Intent(context, WallPaperImageset::class.java)
                    intent.putExtra("imagePath", imageEntity.imagePath)
                    context.startActivity(intent)
                    true
                }
                R.id.action_set_lock_screen -> {

                    deleteImage(imageEntity)
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }
    private fun deleteImage(imageEntity: ImageEntity) {
        (context as? FragmentActivity)?.lifecycleScope?.launch {
            withContext(Dispatchers.IO) {
                imageDao.deleteImage(imageEntity)
            }

            val position = dataList.indexOf(imageEntity)
            if (position != -1) {
                dataList.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }


    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var staggeredImages: RoundedImageView = itemView.findViewById(R.id.staggeredImages)
    }
}
