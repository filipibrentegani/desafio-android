package com.picpay.desafio.android.contacts.data

import com.picpay.desafio.android.User
import retrofit2.Call
import retrofit2.http.GET


interface PicPayService {

    @GET("users")
    fun getUsers(): Call<List<User>>
}