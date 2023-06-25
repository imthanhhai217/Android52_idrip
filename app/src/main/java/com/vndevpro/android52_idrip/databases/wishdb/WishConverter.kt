package com.vndevpro.android52_idrip.databases.wishdb

import androidx.room.TypeConverter

class WishConverter {

    @TypeConverter
    fun formImages(data: List<String>): String {
        var result = ""
        for (i in data.indices) {
            result += data[i]
            if (i < data.size - 1) {
                result += ","
            }
        }
        return result
    }

    @TypeConverter
    fun toImages(data:String):List<String>{
        return data.split(",")
    }

}