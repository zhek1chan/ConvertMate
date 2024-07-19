package com.example.convertmate

import android.app.Application
import com.example.convertmate.di.networkModule
import com.example.convertmate.di.repositoryModule
import com.example.convertmate.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.GlobalContext.startKoin

class App : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                networkModule,
                repositoryModule,
                viewModelModule
            )
        }
        instance = this
    }

    companion object {
        lateinit var instance: App
    }
}

