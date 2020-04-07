package com.mkielar.sklepallegro

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AllegroApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AllegroApplication)
            modules(Module.koin)
        }
    }
}