package com.vndevpro.android52_idrip.databases.wishdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vndevpro.android52_idrip.models.Product
import com.vndevpro.android52_idrip.utils.Constants

@Database(entities = [Product::class], version = 1)
@TypeConverters(WishConverter::class)
abstract class WishListDatabase : RoomDatabase() {

    abstract fun getWishDao(): WishDao

    companion object {

        private var instances: WishListDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) =
            instances ?: synchronized(LOCK) {
            instances ?: createDatabase(context).also {
                instances = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context,
                WishListDatabase::class.java,
                Constants.DB_NAME).build()
    }

}