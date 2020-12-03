package com.example.fetchrewards.viewModel

import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.example.fetchrewards.repository.Item
import com.example.fetchrewards.repository.ItemRepository
import com.example.fetchrewards.utils.SchedulerProvider
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
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

    fun getItems(): Flowable<PagedList<Item>> {
        val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(INITIAL_LOAD_SIZE)
            .setPageSize(PAGE_SIZE)
            .setPrefetchDistance(PREFETCH_DISTANCE)
            .build()
        return RxPagedListBuilder(itemRepository.getItems(), config)
            .buildFlowable(BackpressureStrategy.DROP)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    companion object {
        private const val INITIAL_LOAD_SIZE = 80
        private const val PAGE_SIZE = 40
        private const val PREFETCH_DISTANCE = 20
    }
}