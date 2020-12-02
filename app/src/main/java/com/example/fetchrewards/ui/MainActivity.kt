package com.example.fetchrewards.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.fetchrewards.R
import com.example.fetchrewards.dagger.ComponentProvider
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

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as ComponentProvider).provideAppComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpViewModel()
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this, postsViewModelFactory).get(ItemViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        compositeDisposable.add(
            viewModel.getItems()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.main())
                .subscribe({
                    System.out.println(it)
                }, { throwable ->
                    System.out.println(throwable.message)
                })
        )
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