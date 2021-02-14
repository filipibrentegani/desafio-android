package com.picpay.desafio.android.contacts.domain.di

import com.picpay.desafio.android.contacts.domain.ContactsUseCase
import org.koin.dsl.module

val domainModule = module {
    factory {
        ContactsUseCase(get())
    }
}