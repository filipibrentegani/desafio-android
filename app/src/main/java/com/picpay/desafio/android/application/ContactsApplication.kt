package com.picpay.desafio.android.application

import android.app.Application
import com.picpay.desafio.android.contacts.data.di.contactsDataModule
import com.picpay.desafio.android.contacts.presentation.di.contactsPresentationModule
import org.koin.core.context.startKoin

class ContactsApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(listOf(contactsDataModule, contactsPresentationModule))
        }
    }
}