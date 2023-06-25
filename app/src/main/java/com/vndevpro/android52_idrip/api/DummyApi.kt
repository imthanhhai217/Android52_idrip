package com.vndevpro.android52_idrip.api

import com.vndevpro.android52_idrip.models.ListProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DummyApi {

    @GET("/products")
    suspend fun getListProduct(): Response<ListProductResponse>

    @GET("/products")
    suspend fun getListProduct(@Query("limit") limit: Int): Response<ListProductResponse>

    @GET("/carts")
    suspend fun getListCart(): Response<ListProductResponse>
}