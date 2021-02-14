package com.picpay.desafio.android.contacts.data

import com.picpay.desafio.android.User
import retrofit2.Response

class ContactsRepository(private val service: PicPayService) {

    suspend fun getContacts(): Response<List<User>> {
        return service.getUsers()
    }
}