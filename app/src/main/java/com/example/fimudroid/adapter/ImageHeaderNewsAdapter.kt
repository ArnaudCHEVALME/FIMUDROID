package com.example.fimudroid.adapter;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView
import com.example.fimudroid.R

class ImageHeaderNewsAdapter(
    private val url: String
) : RecyclerView.Adapter<ImageHeaderNewsAdapter.ImageViewHolder>() {

    // Create a new ViewHolder for the image view
    inner class ImageViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val header: ImageView = itemView.findViewById(R.id.header_news)
    }

    // Inflate the XML layout and return a new ViewHolder for the image view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.new_header_recycler, parent, false)
        return ImageViewHolder(view)
    }

    // Bind the image resource to the image view
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.header.setImageResource(R.drawable.fimuapp)
    }

    // Return the number of images in the list
    override fun getItemCount(): Int {
        return 1
    }
}