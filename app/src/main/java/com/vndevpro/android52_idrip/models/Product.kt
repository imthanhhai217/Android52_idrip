package com.vndevpro.android52_idrip.models


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Product(
    @SerializedName("id") @PrimaryKey val id: Int = 0,
//    @ColumnInfo(name = "Brand")
    @SerializedName("brand") val brand: String = "",
    @SerializedName("category") val category: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("discountPercentage") val discountPercentage: Double = 0.0,
    @SerializedName("images") val images: List<String>? = null,
    @SerializedName("price") val price: Int = 0,
    @SerializedName("rating") val rating: Double = 0.0,
    @SerializedName("stock") val stock: Int = 0,
    @SerializedName("thumbnail") val thumbnail: String = "",
    @SerializedName("title") val title: String = "",
    var isWish: Boolean = false
)