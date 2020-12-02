package com.example.fetchrewards.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fetchrewards.R
import com.example.fetchrewards.database.Item
import com.example.fetchrewards.databinding.ItemRowBinding

class ItemViewHolder(
    private val binding: ItemRowBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Item) {
        with(binding) {
            idTextView.text = String.format(idTextView.context.getString(R.string.id_format), item.id)
            listIdTextView.text = String.format(listIdTextView.context.getString(R.string.list_id_format), item.listId)
            nameTextView.text = String.format(nameTextView.context.getString(R.string.name_format), item.name)
        }
    }

    companion object {
        fun create(parent: ViewGroup): ItemViewHolder {
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
            val binding: ItemRowBinding = ItemRowBinding.bind(view)
            return ItemViewHolder(binding)
        }
    }
}