package com.picpay.desafio.android.contacts.presentation

import android.util.Log
import androidx.lifecycle.*
import com.picpay.desafio.android.R
import com.picpay.desafio.android.User
import com.picpay.desafio.android.contacts.domain.ContactsUseCase
import com.picpay.desafio.android.network.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ContactsViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel(),
    KoinComponent {
    private val useCase: ContactsUseCase by inject()

    val contacts: LiveData<List<User>> = useCase.getContactsLiveData()

    val showLoading: LiveData<Boolean> = savedStateHandle.getLiveData(SHOW_LOADING)

    private val errorMLD = MutableLiveData<Int?>()
    val showError: LiveData<Int?> = errorMLD

    fun getContactData() {
        savedStateHandle[SHOW_LOADING] = true
        errorMLD.value = null
        viewModelScope.launch(Dispatchers.IO) {
            val resultWrapper = useCase.updateCachedValues()

            withContext(Dispatchers.Main) {
                savedStateHandle[SHOW_LOADING] = false
                when(resultWrapper) {
                    is ResultWrapper.Success -> {
                        //nothing to do
                    }
                    is ResultWrapper.Error -> {
                        showError()
                        Log.i("ContactsViewModel", resultWrapper.errorValue?.stackTrace.toString())
                    }
                }
            }
        }
    }

    private fun showError() {
        errorMLD.value = R.string.error
    }

    companion object {
        private const val SHOW_LOADING = "showLoading"
    }
}