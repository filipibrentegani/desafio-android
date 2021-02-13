package com.picpay.desafio.android.contacts.presentation.di

import com.picpay.desafio.android.contacts.presentation.ContactsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val contactsPresentationModule = module {
    viewModel {
        ContactsViewModel()
    }
}