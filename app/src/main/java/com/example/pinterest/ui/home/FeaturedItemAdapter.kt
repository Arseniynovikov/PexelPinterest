package com.example.pinterest.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.pinterest.R

class FeaturedItemAdapter(
    private val items: MutableList<String>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<FeaturedItemAdapter.ViewHolder>() {

    private var selected: String? = null

    inner class ViewHolder(private val text: TextView) : RecyclerView.ViewHolder(text) {
        fun bind(item: String) {
            text.text = item

            if (item == selected) {
                text.setBackgroundResource(R.drawable.featured_selected)
                text.setTextColor(ContextCompat.getColor(text.context, android.R.color.white))
            } else {
                text.setBackgroundResource(R.drawable.featured)
                text.setTextColor(ContextCompat.getColor(text.context, android.R.color.black))

            }

            text.setOnClickListener {
                selected = item
                val pos = items.indexOf(item)
                if (pos > 0) {
                    items.removeAt(pos)
                    items.add(0, item)
                    notifyItemMoved(pos, 0)
                }
                notifyDataSetChanged()
                onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val tv = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_featured, parent, false) as TextView
        return ViewHolder(tv)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun reset(new: List<String>) {
        items.clear()
        items.addAll(new)
        selected = null
        notifyDataSetChanged()
    }
}