package com.vndevpro.android52_idrip.repositories

import com.vndevpro.android52_idrip.databases.wishdb.WishListDatabase
import com.vndevpro.android52_idrip.models.Product

class WishListRepository(private val wishListDatabase: WishListDatabase) {

    suspend fun upsertWish(product: Product) = wishListDatabase.getWishDao().upsertWish(product)

    suspend fun deleteWish(product: Product) = wishListDatabase.getWishDao().deleteWish(product)

    fun getAllWishList() = wishListDatabase.getWishDao().getAllWishList()
}