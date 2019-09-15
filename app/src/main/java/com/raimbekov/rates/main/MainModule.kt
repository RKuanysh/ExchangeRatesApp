package com.raimbekov.rates.main

import com.raimbekov.rates.main.domain.RatesInteractor
import com.raimbekov.rates.main.domain.RatesLocalRepository
import com.raimbekov.rates.main.domain.RatesRemoteRepository
import com.raimbekov.rates.main.repository.RatesInMemoryRepository
import com.raimbekov.rates.main.repository.RatesRestRepository
import com.raimbekov.rates.main.repository.RatesService
import com.raimbekov.rates.main.view.MainViewModel
import org.koin.dsl.module
import retrofit2.Retrofit

object MainModule {

    val module = module {

        factory<RatesService> {
            get<Retrofit>().create(RatesService::class.java)
        }

        factory<RatesLocalRepository> { RatesInMemoryRepository() }
        factory<RatesRemoteRepository> { RatesRestRepository(get()) }

        factory { RatesInteractor(get(), get()) }

        factory { MainViewModel(get()) }
    }
}