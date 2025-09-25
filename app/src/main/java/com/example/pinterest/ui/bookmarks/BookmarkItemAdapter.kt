package com.example.pinterest.ui.bookmarks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pinterest.R
import com.example.pinterest.data.models.UIImage
import com.google.android.material.imageview.ShapeableImageView

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
        private val imageView: ShapeableImageView = itemView.findViewById(R.id.imageView)
        private val name: TextView = itemView.findViewById(R.id.nameTextView)

        fun bind(image: UIImage) {
            Glide.with(imageView.context)
                .load(image.mediumSrc)
                .placeholder(R.drawable.placeholder)
                .fitCenter()
                .into(imageView)

            name.text = image.photographer
            itemView.setOnClickListener { onClick(image) }
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
