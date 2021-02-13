package com.picpay.desafio.android.contacts.domain

import com.picpay.desafio.android.User
import retrofit2.Callback

interface IContactsRepository {
    fun getContacts(callback: Callback<List<User>>)
}