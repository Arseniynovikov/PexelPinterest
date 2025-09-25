package com.example.pinterest.ui.bookmarks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pinterest.R
import com.example.pinterest.data.models.BookmarkImages
import com.example.pinterest.data.models.UIImage

class BookmarkItemAdapter(
    private val onClick: (UIImage) -> Unit
) : ListAdapter<UIImage, BookmarkItemAdapter.BookmarkItemViewHolder>(DiffCallback) {

    object DiffCallback : DiffUtil.ItemCallback<UIImage>() {
        override fun areItemsTheSame(oldItem: UIImage, newItem: UIImage) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UIImage, newItem: UIImage) =
            oldItem == newItem
    }

    inner class BookmarkItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(images: UIImage) {
            Glide.with(imageView.context)
                .load(images.mediumSrc)
                .placeholder(R.drawable.placeholder)
                .centerCrop()
                .into(imageView)

            itemView.setOnClickListener { onClick(images) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bookmark, parent, false)
        return BookmarkItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookmarkItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
