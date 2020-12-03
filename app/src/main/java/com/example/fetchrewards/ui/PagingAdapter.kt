package com.example.fetchrewards.ui

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.fetchrewards.repository.Item

class PagingAdapter : PagedListAdapter<Item, ItemViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldConcert: Item, newConcert: Item) = oldConcert.id == newConcert.id

            override fun areContentsTheSame(oldConcert: Item, newConcert: Item) = oldConcert == newConcert
        }
    }
}