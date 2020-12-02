package com.example.fetchrewards.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fetchrewards.dagger.ComponentProvider
import com.example.fetchrewards.database.Item
import com.example.fetchrewards.databinding.ActivityMainBinding
import com.example.fetchrewards.utils.SchedulerProvider
import com.example.fetchrewards.viewModel.ItemViewModel
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
    private lateinit var adapter: ItemAdapter

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
        adapter = ItemAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        compositeDisposable.add(
            viewModel.getItems()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.main())
                .subscribe({items ->
                    updateUI(items)
                }, { throwable ->
                    System.out.println(throwable.message)
                })
        )
    }

    private fun updateUI(items: List<Item>) {
        adapter.setItems(items)
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