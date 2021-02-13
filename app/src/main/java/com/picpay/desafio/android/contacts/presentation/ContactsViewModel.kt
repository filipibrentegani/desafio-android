package com.picpay.desafio.android.contacts.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.picpay.desafio.android.R
import com.picpay.desafio.android.User
import com.picpay.desafio.android.contacts.data.ContactsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactsViewModel: ViewModel(), KoinComponent {
    private val repository: ContactsRepository by inject()

    private val contactsMutableLiveData = MutableLiveData<List<User>>()
    val contactsLiveData: LiveData<List<User>> = contactsMutableLiveData

    private val showContactsMutableLiveData = MutableLiveData<Boolean>()
    val showContactsLiveData: LiveData<Boolean> = showContactsMutableLiveData

    private val showLoadingMutableLiveData = MutableLiveData<Boolean>()
    val showLoadingLiveData: LiveData<Boolean> = showLoadingMutableLiveData

    private val errorMutableLiveData = MutableLiveData<Int>()
    val errorLoadingLiveData: LiveData<Int> = errorMutableLiveData

    fun getContactData() {
        showLoadingMutableLiveData.value = true
        showContactsMutableLiveData.value = false
        repository.getContacts(object : Callback<List<User>> {
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                showLoadingMutableLiveData.value = false
                showContactsMutableLiveData.value = false
                errorMutableLiveData.value = R.string.error
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                showLoadingMutableLiveData.value = false
                showContactsMutableLiveData.value = true
                contactsMutableLiveData.value = response.body()
            }
        })
    }
}