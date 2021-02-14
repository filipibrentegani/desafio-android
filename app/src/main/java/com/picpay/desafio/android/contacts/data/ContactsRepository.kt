package com.picpay.desafio.android.contacts.data

import androidx.lifecycle.LiveData
import com.picpay.desafio.android.User
import com.picpay.desafio.android.contacts.domain.IContactsRepository
import com.picpay.desafio.android.network.ResultWrapper
import kotlin.Exception

class ContactsRepository(
    private val service: PicPayService,
    private val userDao: UserDao
): IContactsRepository {

    override fun getContactsLiveData(): LiveData<List<User>> {
        return userDao.getAll()
    }

    override suspend fun updateCachedValues(): ResultWrapper<List<User>, Exception> {
        var users: List<User>? = null

        val resultWrapper = try {
            users = service.getUsers()
            ResultWrapper.Success<List<User>, Exception>(users)
        } catch (ex: Exception) {
            ResultWrapper.Error<List<User>, Exception>(ex)
        }

        users?.let {
            userDao.insertAll(it)
        }
        return resultWrapper
    }
}