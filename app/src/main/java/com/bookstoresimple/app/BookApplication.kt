package com.bookstoresimple.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BookApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BookApplication)
            modules(
                appModule,
                cacheModule,
                dataModule,
                domainModule,
                presentationModule,
                remoteModule,
                useCaseModule,
                mobileUiModule
            )
        }
    }
}