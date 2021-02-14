package com.picpay.desafio.android.utils.di

import com.picpay.desafio.android.utils.ConnectivityUtils
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val utilsModule = module {
    factory {
        ConnectivityUtils(androidContext())
    }
}