package com.picpay.desafio.android.application

import android.app.Application
import com.picpay.desafio.android.contacts.data.di.contactsDataModule
import com.picpay.desafio.android.contacts.presentation.di.contactsPresentationModule
import com.picpay.desafio.android.utils.di.utilsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ContactsApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ContactsApplication)

            modules(listOf(contactsDataModule, contactsPresentationModule, utilsModule))
        }
    }
}