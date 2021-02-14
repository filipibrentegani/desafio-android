package com.picpay.desafio.android.contacts.presentation

import androidx.lifecycle.*
import com.picpay.desafio.android.R
import com.picpay.desafio.android.User
import com.picpay.desafio.android.contacts.data.ContactsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ContactsViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel(),
    KoinComponent {
    private val repository: ContactsRepository by inject()

    val contacts: LiveData<List<User>> = savedStateHandle.getLiveData("contacts")

    val showContacts: LiveData<Boolean> = savedStateHandle.getLiveData("showContacts")

    val showLoading: LiveData<Boolean> = savedStateHandle.getLiveData("showLoading")

    private val errorMLD = MutableLiveData<Int?>()
    val showError: LiveData<Int?> = errorMLD

    fun getContactData() {
        savedStateHandle["showLoading"] = true
        savedStateHandle["showContacts"] = false
        errorMLD.value = null
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getContacts()

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    savedStateHandle["showContacts"] = true
                    savedStateHandle["showLoading"] = false
                    savedStateHandle["contacts"] = response.body()
                } else {
                    savedStateHandle["showLoading"] = false
                    savedStateHandle["showContacts"] = false
                    errorMLD.value = R.string.error
                }
            }
        }
    }
}