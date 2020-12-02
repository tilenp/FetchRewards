package com.example.fetchrewards.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fetchrewards.database.Item
import java.util.ArrayList

class ItemAdapter : RecyclerView.Adapter<ItemViewHolder>() {

    private var items: List<Item> = ArrayList()

    fun setItems(items: List<Item>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }
}