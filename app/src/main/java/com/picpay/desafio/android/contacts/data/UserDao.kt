package com.picpay.desafio.android.contacts.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.picpay.desafio.android.User

@Dao
interface UserDao {
    @Query("select * from user")
    fun getAll(): LiveData<List<User>>

    @Query("delete from user")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>)
}