package com.picpay.desafio.android.contacts.data

import com.picpay.desafio.android.User
import retrofit2.Callback

class ContactsRepository(private val service: PicPayService) {

    fun getContacts(callback: Callback<List<User>>) {
        service.getUsers()
            .enqueue(callback)
    }
}