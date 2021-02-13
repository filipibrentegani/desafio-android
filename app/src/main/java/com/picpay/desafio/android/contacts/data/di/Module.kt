package com.picpay.desafio.android.contacts.data.di

import com.picpay.desafio.android.contacts.data.PicPayService
import com.picpay.desafio.android.contacts.data.ContactsRepository
import com.picpay.desafio.android.contacts.domain.IContactsRepository
import com.picpay.desafio.android.network.retrofit
import org.koin.dsl.binds
import org.koin.dsl.module

val contactsDataModule = module {
    factory<PicPayService> {
        retrofit.create(PicPayService::class.java)
    }

    factory {
        ContactsRepository(get())
    } binds arrayOf(IContactsRepository::class, ContactsRepository::class) //para o Koin aceitar a injeção nos 2 tipos
}