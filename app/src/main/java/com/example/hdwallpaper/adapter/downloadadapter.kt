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
import com.example.hdwallpaper.activity.WallPaperImageset
import com.example.hdwallpaper.roomdb.ImageEntity
import com.makeramen.roundedimageview.RoundedImageView

class downloadadapter(private val dataList: List<ImageEntity>, private val context: Context) :
    RecyclerView.Adapter<downloadadapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.staggered_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val imagePath = dataList[position].imagePath

        if (imagePath != null) {
            Glide.with(context)
                .load(imagePath)
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


        holder.staggeredImages.setOnClickListener {
            val intent = Intent(context, WallPaperImageset::class.java)
            intent.putExtra("imagePath", imagePath)
            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var staggeredImages: RoundedImageView = itemView.findViewById(R.id.staggeredImages)
    }
}
