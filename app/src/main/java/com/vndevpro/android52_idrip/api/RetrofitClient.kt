package com.vndevpro.android52_idrip.api

import com.vndevpro.android52_idrip.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object {
        private val instances by lazy {
            if (Constants.DEBUG) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                val okHttpClient =
                    OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
                Retrofit.Builder().baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
            } else {
                Retrofit.Builder().baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build()
            }
        }

        val getDummyApi: DummyApi = instances.create(DummyApi::class.java)
    }

}