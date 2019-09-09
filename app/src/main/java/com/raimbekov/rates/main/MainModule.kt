package com.raimbekov.rates.main

import com.raimbekov.rates.main.repository.RatesService
import org.koin.dsl.module
import retrofit2.Retrofit

object MainModule {

    val module = module {

        factory<RatesService> {
            get<Retrofit>().create(RatesService::class.java)
        }
    }
}