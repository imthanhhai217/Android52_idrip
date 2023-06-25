package com.vndevpro.android52_idrip.databases.wishdb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vndevpro.android52_idrip.models.Product

@Dao
interface WishDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertWish(product: Product)

    @Delete
    suspend fun deleteWish(product: Product)

    @Query("SELECT * FROM Product")
    fun getAllWishList():LiveData<List<Product>>
}