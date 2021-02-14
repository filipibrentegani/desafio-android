package com.picpay.desafio.android.contacts.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.picpay.desafio.android.User
import com.picpay.desafio.android.network.ResultWrapper

class ContactsUseCase(private val repository: IContactsRepository) {
    fun getContactsLiveData(): LiveData<List<User>> {
        return repository.getContactsLiveData().map { usersList ->
            usersList.sortedBy { user -> user.name }
        }
        //Utilizei o usecase para um exemplo de ordenação, mas toda lógica de negócio
        //que tivermos deve estar nessa classe
    }

    suspend fun updateCachedValues() : ResultWrapper<List<User>, Exception> {
        return repository.updateCachedValues()
    }
}