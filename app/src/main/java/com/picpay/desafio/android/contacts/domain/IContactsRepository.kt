package com.picpay.desafio.android.contacts.domain

import androidx.lifecycle.LiveData
import com.picpay.desafio.android.contacts.data.User
import com.picpay.desafio.android.network.ResultWrapper

interface IContactsRepository {
    fun getContactsLiveData(): LiveData<List<User>>

    suspend fun updateCachedValues(): ResultWrapper<List<User>, Exception>
}