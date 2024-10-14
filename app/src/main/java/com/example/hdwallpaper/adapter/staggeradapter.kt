package com.example.hdwallpaper.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hdwallpaper.R

import com.example.hdwallpaper.activity.imageselection

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
        // Load the image using Glide
        Glide.with(context).load(dataList[position].imageURL).into(holder.staggeredImages)

        // Randomize height for the staggered effect
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

        // Handle image click to open SetDownloadpic activity
        holder.staggeredImages.setOnClickListener {
            val imageUrl = dataList[position].imageURL
            val intent = Intent(context, imageselection::class.java)
            intent.putExtra("IMAGE_RES_ID", imageUrl)
            context.startActivity(intent)
            Toast.makeText(context, "Item clicked", Toast.LENGTH_SHORT).show()
        }

        // Set touch listener to show/hide download icon
//        holder.staggeredImages.setOnTouchListener { _, event ->
//            when (event.action) {
//                android.view.MotionEvent.ACTION_DOWN -> {
//                    holder.downloadIcon.visibility = View.VISIBLE // Show icon
//                }
//                android.view.MotionEvent.ACTION_UP, android.view.MotionEvent.ACTION_CANCEL -> {
//                    holder.downloadIcon.visibility = View.GONE // Hide icon after interaction
//                }
//            }
//            true // Return true to indicate the event was handled
//        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var staggeredImages: RoundedImageView = itemView.findViewById(R.id.staggeredImages)
        var downloadIcon: ImageView = itemView.findViewById(R.id.downloadIcon)
    }
}
