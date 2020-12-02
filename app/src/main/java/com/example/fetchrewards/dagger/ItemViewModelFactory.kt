package com.example.fetchrewards.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fetchrewards.repository.ItemRepository
import com.example.fetchrewards.viewModel.ItemViewModel
import com.example.fetchrewards.utils.SchedulerProvider

@Suppress("UNCHECKED_CAST")
class ItemViewModelFactory constructor(
    private val itemsRepository: ItemRepository,
    private val schedulerProvider: SchedulerProvider
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ItemViewModel(itemsRepository, schedulerProvider) as T
    }
}