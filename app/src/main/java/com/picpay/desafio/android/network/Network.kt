package com.picpay.desafio.android.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val url = "http://careers.picpay.com/tests/mobdev/"

private val gson: Gson by lazy { GsonBuilder().create() }

private val okHttp: OkHttpClient by lazy {
    OkHttpClient.Builder()
        .build()
}

val retrofit: Retrofit by lazy {
    Retrofit.Builder()
        .baseUrl(url)
        .client(okHttp)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}