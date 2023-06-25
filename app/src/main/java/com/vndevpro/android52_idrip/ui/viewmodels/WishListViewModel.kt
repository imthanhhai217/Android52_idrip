package com.vndevpro.android52_idrip.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.vndevpro.android52_idrip.models.Product
import com.vndevpro.android52_idrip.repositories.WishListRepository
import kotlinx.coroutines.launch

class WishListViewModel(
    private val wishListRepository: WishListRepository, application: Application
) : AndroidViewModel(application) {

    fun upsertWish(product: Product) = viewModelScope.launch {
        wishListRepository.upsertWish(product = product)
    }

    fun deleteWish(product: Product) = viewModelScope.launch {
        wishListRepository.deleteWish(product)
    }

    fun getAllWishList() = wishListRepository.getAllWishList()
}