package com.picpay.desafio.android.contacts.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.picpay.desafio.android.User
import com.picpay.desafio.android.contacts.domain.IContactsRepository
import com.picpay.desafio.android.utils.ConnectivityUtils
import retrofit2.Response
import java.lang.Exception

class ContactsRepository(
    private val service: PicPayService,
    private val userDao: UserDao,
    private val connectivityUtils: ConnectivityUtils
): IContactsRepository {

    override fun getContactsLiveData(): LiveData<List<User>> {
        return userDao.getAll()
    }

    override suspend fun updateCachedValues(): Response<List<User>>? {
        var response: Response<List<User>>? = null

        if (!connectivityUtils.hasConnectivity()) {
            return null
        }

        try {
            response = service.getUsers()
        } catch (ex: Exception) {
            Log.i("filipi", ex.toString()) //o tratamento de erros pode ser infinitamente melhorado
        }

        response?.let {
            userDao.insertAll(it.body() ?: listOf())
            return it
        }
        return null
    }
}