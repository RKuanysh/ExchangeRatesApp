package com.raimbekov.rates.common

import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CommonModule {

    val module = module {

        single { OkHttpClient() }

        single<Retrofit> {
            Retrofit.Builder()
                .baseUrl("https://revolut.duckdns.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(get())
                .build()
        }
    }
}