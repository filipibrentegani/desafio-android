package com.picpay.desafio.android.contacts.domain

import androidx.lifecycle.LiveData
import com.picpay.desafio.android.User
import retrofit2.Response

interface IContactsRepository {
    fun getContactsLiveData(): LiveData<List<User>>

    suspend fun updateCachedValues(): Response<List<User>>?
}