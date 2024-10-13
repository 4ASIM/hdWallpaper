package com.example.hdwallpaper.adapter

import com.example.hdwallpaper.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hdwallpaper.dataclasses.dataclass

import com.makeramen.roundedimageview.RoundedImageView

class staggeradapter(private val dataList: ArrayList<dataclass>, private val context: Context) :
    RecyclerView.Adapter<staggeradapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.staggered_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Load the image
        Glide.with(context).load(dataList[position].imageURL).into(holder.staggeredImages)

        // Randomize height for a staggered effect
        val params = holder.staggeredImages.layoutParams
        params.height = (200..400).random()
        holder.staggeredImages.layoutParams = params
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var staggeredImages: RoundedImageView = itemView.findViewById(R.id.staggeredImages)
    }
}
