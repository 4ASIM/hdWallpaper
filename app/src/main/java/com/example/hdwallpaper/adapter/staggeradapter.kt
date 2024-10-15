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
import com.example.hdwallpaper.activity.ImageDownloadAcitvity

import com.example.hdwallpaper.dataclasses.dataclass
import com.makeramen.roundedimageview.RoundedImageView

class staggeradapter(private val dataList: ArrayList<dataclass>, private val context: Context) :
    RecyclerView.Adapter<staggeradapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.staggered_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        Glide.with(context)
            .load(dataList[position].imageURL)
            .placeholder(R.drawable.placeholder_image)
            .into(holder.staggeredImages)
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


        holder.staggeredImages.setOnClickListener {
            val imageUrl = dataList[position].imageURL
            if (!imageUrl.isNullOrEmpty()) {
                val intent = Intent(context, ImageDownloadAcitvity::class.java)
                intent.putExtra("IMAGE_URL", imageUrl)
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "Image URL is invalid", Toast.LENGTH_SHORT).show()
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
