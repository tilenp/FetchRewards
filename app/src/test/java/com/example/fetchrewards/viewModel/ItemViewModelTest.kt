package com.example.fetchrewards.viewModel

import com.example.fetchrewards.repository.ItemRepository
import com.example.fetchrewards.utils.TestSchedulerProvider
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test

class ItemViewModelTest {

    private val itemRepository: ItemRepository = mock()
    private lateinit var viewModel: ItemViewModel

    @Before
    fun setUp() {
        whenever(itemRepository.updateItems()).thenReturn(Completable.complete())
        viewModel = ItemViewModel(itemRepository, TestSchedulerProvider())
    }

    @Test
    fun when_viewModel_is_instantiated_items_are_updated() {
        // assert
        verify(itemRepository, times(1)).updateItems()
    }
}