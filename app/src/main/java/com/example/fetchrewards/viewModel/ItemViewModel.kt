package com.example.fetchrewards.viewModel

import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.example.fetchrewards.R
import com.example.fetchrewards.repository.Item
import com.example.fetchrewards.repository.ItemRepository
import com.example.fetchrewards.utils.SchedulerProvider
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class ItemViewModel @Inject constructor(
    private val itemRepository: ItemRepository,
    private val schedulerProvider: SchedulerProvider
): ViewModel() {

    private val uiStateSubject = BehaviorSubject.create<UIState>()
    private val compositeDisposable = CompositeDisposable()

    init {
        updateItems()
    }

    private fun updateItems() {
        compositeDisposable.add(
            itemRepository.updateItems()
                .doOnError { uiStateSubject.onNext(UIState.Error(R.string.network_error)) }
                .onErrorComplete()
                .andThen(itemRepository.getItemCount()
                    .doOnError { uiStateSubject.onNext(UIState.Error(R.string.database_error)) })
                .map { itemCount -> if (itemCount == 0) UIState.NoItems else UIState.Success }
                .doOnSubscribe { uiStateSubject.onNext(UIState.Loading) }
                .subscribeOn(schedulerProvider.io())
                .subscribe({
                    uiStateSubject.onNext(it)
                }, {
                    System.out.println("TTT")
                })
        )
    }

    fun getUIState(): Observable<UIState> {
        return uiStateSubject
    }

    fun getItems(): Flowable<PagedList<Item>> {
        val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(INITIAL_LOAD_SIZE)
            .setPageSize(PAGE_SIZE)
            .setPrefetchDistance(PREFETCH_DISTANCE)
            .build()
        return RxPagedListBuilder(itemRepository.getItems(), config)
            .buildFlowable(BackpressureStrategy.DROP)
            .doOnError {
                uiStateSubject.onNext(UIState.Error(R.string.database_error))
                uiStateSubject.onNext(UIState.NoItems)
            }
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