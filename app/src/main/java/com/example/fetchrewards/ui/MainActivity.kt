package com.example.fetchrewards.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fetchrewards.dagger.ComponentProvider
import com.example.fetchrewards.databinding.ActivityMainBinding
import com.example.fetchrewards.repository.Item
import com.example.fetchrewards.utils.SchedulerProvider
import com.example.fetchrewards.viewModel.ItemViewModel
import com.example.fetchrewards.viewModel.UIState
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    @Inject
    lateinit var postsViewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ItemViewModel
    private val compositeDisposable = CompositeDisposable()

    private lateinit var binding: ActivityMainBinding
    private lateinit var pagingAdapter: PagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as ComponentProvider).provideAppComponent().inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViewModel()
        setUpUI()
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this, postsViewModelFactory).get(ItemViewModel::class.java)
    }

    private fun setUpUI() {
        pagingAdapter = PagingAdapter()
        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerView.adapter = pagingAdapter
            recyclerView.addItemDecoration(MyItemDecorator(this@MainActivity))
            retryTextView.setOnClickListener { viewModel.retry() }
        }
    }

    override fun onStart() {
        super.onStart()
        compositeDisposable.add(
            viewModel.getUIState()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.main())
                .subscribe({
                    handleUIState(it)
                }, {})
        )

        compositeDisposable.add(
            viewModel.getItems()
                .subscribe({ items ->
                    updateItems(items)
                }, {})
        )
    }

    private fun handleUIState(state: UIState) {
        when (state) {
            is UIState.Loading -> setLoadingState()
            is UIState.Success -> setSuccessState()
            is UIState.NoItems -> setNoItemsState()
            is UIState.Error -> handleErrors(state.stringId)
        }
    }

    private fun setLoadingState() {
        with(binding) {
            progressBar.visibility = View.VISIBLE
            retryTextView.visibility = View.GONE
            recyclerView.visibility = View.GONE
        }
    }

    private fun setSuccessState() {
        with(binding) {
            progressBar.visibility = View.GONE
            retryTextView.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    private fun setNoItemsState() {
        with(binding) {
            progressBar.visibility = View.GONE
            retryTextView.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        }
    }

    private fun handleErrors(stringId: Int) {
        Toast.makeText(this, getString(stringId), Toast.LENGTH_SHORT).show()
    }

    private fun updateItems(items: PagedList<Item>) {
        pagingAdapter.submitList(items)
    }

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}