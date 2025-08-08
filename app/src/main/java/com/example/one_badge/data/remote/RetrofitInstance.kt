package com.example.one_badge.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val logger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY  // Logs full URL + headers + body
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logger)
        .build()

    val api: SportsDbApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.thesportsdb.com/")
            .client(client)   // Set client with logging
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SportsDbApi::class.java)
    }
}