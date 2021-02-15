package com.picpay.desafio.android.contacts.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.picpay.desafio.android.R
import com.picpay.desafio.android.contacts.data.User
import com.picpay.desafio.android.contacts.domain.ContactsUseCase
import com.picpay.desafio.android.network.ResultWrapper
import com.picpay.desafio.android.ui.ioJob
import com.picpay.desafio.android.ui.ui
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
        ioJob {
            val resultWrapper = useCase.updateCachedValues()

            withContext(ui) {
                savedStateHandle[SHOW_LOADING] = false
                when (resultWrapper) {
                    is ResultWrapper.Success -> {
                        //nothing to do
                    }
                    is ResultWrapper.Error -> {
                        showError()
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