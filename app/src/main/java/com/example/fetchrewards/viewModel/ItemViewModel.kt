package com.example.fetchrewards.viewModel

import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.fetchrewards.repository.Item
import com.example.fetchrewards.repository.ItemRepository
import com.example.fetchrewards.utils.SchedulerProvider
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ItemViewModel @Inject constructor(
    private val itemRepository: ItemRepository,
    private val schedulerProvider: SchedulerProvider
): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    init {
        updatePosts()
    }

    private fun updatePosts() {
        compositeDisposable.add(
            itemRepository.updateItems()
                .subscribeOn(schedulerProvider.io())
                .subscribe({}, {
                    System.out.println(it.message)
                })
        )
    }

    fun getItems(): Observable<List<Item>> {
        return itemRepository.getItems()
    }

    fun getI(): Flowable<PagedList<Item>> {
        return itemRepository.getI()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}