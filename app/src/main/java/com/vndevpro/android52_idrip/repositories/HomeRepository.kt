package com.vndevpro.android52_idrip.repositories

import com.vndevpro.android52_idrip.api.RetrofitClient

class HomeRepository {
    suspend fun getListProduct() = RetrofitClient.getDummyApi.getListProduct()
}