package com.vndevpro.android52_idrip.ui.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vndevpro.android52_idrip.repositories.HomeRepository

class HomeViewModelFactory(
    private val homeRepository: HomeRepository, private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(homeRepository = homeRepository, application = application) as T
    }
}