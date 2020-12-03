package com.example.fetchrewards.viewModel

import com.example.fetchrewards.R
import com.example.fetchrewards.repository.ItemRepository
import com.example.fetchrewards.utils.TestSchedulerProvider
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.Test

class ItemViewModelTest {

    private val itemRepository: ItemRepository = mock()
    private lateinit var viewModel: ItemViewModel

    private fun setUp(
        updateItemsRequest: Completable,
        itemCountQuery: Observable<Int>
    ) {
        whenever(itemRepository.updateItems()).thenReturn(updateItemsRequest)
        whenever(itemRepository.getItemCount()).thenReturn(itemCountQuery)
        viewModel = ItemViewModel(itemRepository, TestSchedulerProvider())
    }

    @Test
    fun when_viewModel_is_instantiated_items_are_updated() {
        // arrange
        setUp(
            updateItemsRequest = Completable.complete(),
            itemCountQuery = Observable.just(1)
        )

        // assert
        verify(itemRepository, times(1)).updateItems()
    }

    @Test
    fun when_items_are_loading_ui_state_is_correct() {
        // arrange
        setUp(
            updateItemsRequest = Completable.never(),
            itemCountQuery = Observable.never()
        )

        // assert
        viewModel.getUIState()
            .test()
            .assertValue(UIState.Loading)
            .dispose()
    }

    @Test
    fun when_network_request_and_database_query_are_successful_ui_state_is_correct() {
        // arrange
        setUp(
            updateItemsRequest = Completable.complete(),
            itemCountQuery = Observable.just(1)
        )

        // assert
        viewModel.getUIState()
            .test()
            .assertValue(UIState.Success)
            .dispose()
    }

    @Test
    fun when_network_request_fails_error_is_correct() {
        // arrange
        setUp(
            updateItemsRequest = Completable.error(Throwable()),
            itemCountQuery = Observable.never()
        )

        // assert
        viewModel.getUIState()
            .test()
            .assertValue(UIState.Error(R.string.network_error))
            .dispose()
    }

    @Test
    fun when_network_request_fails_but_database_contains_elements_ui_state_is_set_correctly() {
        // arrange
        setUp(
            updateItemsRequest = Completable.error(Throwable()),
            itemCountQuery = Observable.just(1)
        )

        // assert
        viewModel.getUIState()
            .test()
            .assertValue(UIState.Success)
            .dispose()
    }

    @Test
    fun when_network_request_fails_and_database_is_empty_ui_state_is_set_correctly() {
        // arrange
        setUp(
            updateItemsRequest = Completable.error(Throwable()),
            itemCountQuery = Observable.just(0)
        )

        // assert
        viewModel.getUIState()
            .test()
            .assertValue(UIState.NoItems)
            .dispose()
    }
}