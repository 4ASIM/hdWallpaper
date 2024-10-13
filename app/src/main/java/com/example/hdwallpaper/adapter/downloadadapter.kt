package com.example.hdwallpaper.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hdwallpaper.R
import com.example.hdwallpaper.dataclasses.dataclass
import com.makeramen.roundedimageview.RoundedImageView

class downloadadapter(private val dataList: ArrayList<dataclass>, private val context: Context) :
    RecyclerView.Adapter<downloadadapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.staggered_item, parent, false)
        return MyViewHolder(view)
    }

//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        // Load the image
//        Glide.with(context).load(dataList[position].imageURL).into(holder.staggeredImages)
//
//        // Randomize height for a staggered effect
//        val params = holder.staggeredImages.layoutParams
//        params.height = (200..400).random()
//        holder.staggeredImages.layoutParams = params
//    }
override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    Glide.with(context).load(dataList[position].imageURL).into(holder.staggeredImages)

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
}

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var staggeredImages: RoundedImageView = itemView.findViewById(R.id.staggeredImages)
    }
}
