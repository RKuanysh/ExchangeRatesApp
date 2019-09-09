package com.raimbekov.rates.main

import com.raimbekov.rates.main.domain.RatesInteractor
import com.raimbekov.rates.main.domain.RatesRepository
import com.raimbekov.rates.main.repository.RatesRemoteRepository
import com.raimbekov.rates.main.repository.RatesService
import com.raimbekov.rates.main.view.MainViewModel
import org.koin.dsl.module
import retrofit2.Retrofit

object MainModule {

    val module = module {

        factory<RatesService> {
            get<Retrofit>().create(RatesService::class.java)
        }

        factory<RatesRepository> { RatesRemoteRepository(get()) }

        factory { RatesInteractor(get()) }

        factory { MainViewModel(get()) }
    }
}