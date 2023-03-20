package com.accenture.mvi.presentation

import android.app.Application
import com.accenture.mvi.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MVIApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MVIApp)
            modules(appModule)
        }
    }
}