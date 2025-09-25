package com.example.pinterest.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pinterest.R
import com.example.pinterest.data.pexelModels.Photo
import com.google.android.material.imageview.ShapeableImageView

class HomeItemPhotoAdapter( private val onClick: (Photo) -> Unit
) : ListAdapter<Photo, HomeItemPhotoAdapter.HomeItemPhotoViewHolder>(DiffCallback) {

    object DiffCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Photo, newItem: Photo) = oldItem == newItem
    }

    inner class HomeItemPhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ShapeableImageView = itemView.findViewById(R.id.imageView)

        fun bind(photo: Photo) {
            Glide.with(imageView.context)
                .load(photo.src.medium)
                .placeholder(R.drawable.placeholder)
                .fitCenter()
                .into(imageView)

            itemView.setOnClickListener { onClick(photo) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemPhotoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_photo, parent, false)
        return HomeItemPhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeItemPhotoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}