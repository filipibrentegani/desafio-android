package com.picpay.desafio.android.contacts.data

import com.picpay.desafio.android.User
import retrofit2.http.GET

interface PicPayService {
    @GET("users")
    suspend fun getUsers(): List<User>
}