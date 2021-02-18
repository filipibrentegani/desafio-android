package com.picpay.desafio.android

import android.app.Application
import com.picpay.desafio.android.contacts.data.di.contactsDataModule
import com.picpay.desafio.android.contacts.domain.di.domainModule
import com.picpay.desafio.android.contacts.presentation.di.contactsPresentationModule
import com.picpay.desafio.android.network.url
import com.picpay.desafio.android.utils.di.utilsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PicPayTestApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        url = "http://localhost:8080/"

        startKoin {
            androidContext(this@PicPayTestApplication)

            modules(
                listOf(
                    contactsDataModule,
                    contactsPresentationModule,
                    domainModule,
                    utilsModule
                )
            )
        }
    }
}