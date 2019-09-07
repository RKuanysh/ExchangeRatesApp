package com.raimbekov.rates

import android.app.Application
import com.raimbekov.rates.main.MainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RatesApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@RatesApplication)
            modules(MainModule.module)
        }
    }
}