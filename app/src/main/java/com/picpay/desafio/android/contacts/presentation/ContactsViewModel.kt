package com.picpay.desafio.android.contacts.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.picpay.desafio.android.R
import com.picpay.desafio.android.User
import com.picpay.desafio.android.contacts.data.ContactsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactsViewModel(private val savedStateHandle: SavedStateHandle): ViewModel(), KoinComponent {
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
        repository.getContacts(object : Callback<List<User>> {
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                savedStateHandle["showLoading"] = false
                savedStateHandle["showContacts"] = false
                errorMLD.value = R.string.error
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                savedStateHandle["showContacts"] = true
                savedStateHandle["showLoading"] = false
                savedStateHandle["contacts"] = response.body()
            }
        })
    }
}