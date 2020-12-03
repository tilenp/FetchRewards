package com.example.fetchrewards.viewModel

import androidx.annotation.StringRes

sealed class UIState {
    object Loading : UIState()
    object Success : UIState()
    object NoItems : UIState()
    data class Error(@StringRes val stringId: Int) : UIState()
}