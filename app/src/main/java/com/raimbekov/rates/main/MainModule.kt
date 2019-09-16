package com.raimbekov.rates.main

import com.raimbekov.rates.main.domain.GetRatesUseCase
import com.raimbekov.rates.main.domain.RatesRepository
import com.raimbekov.rates.main.repository.RatesRepositoryImpl
import com.raimbekov.rates.main.repository.RatesService
import com.raimbekov.rates.main.repository.local.RatesInMemoryRepository
import com.raimbekov.rates.main.repository.local.RatesLocalRepository
import com.raimbekov.rates.main.repository.remote.RatesRemoteRepository
import com.raimbekov.rates.main.repository.remote.RatesRestRepository
import com.raimbekov.rates.main.view.RatesViewModel
import org.koin.dsl.module
import retrofit2.Retrofit

object MainModule {

    val module = module {

        factory { RatesViewModel(get()) }

        factory { GetRatesUseCase(get()) }

        factory<RatesLocalRepository> { RatesInMemoryRepository() }
        factory<RatesRemoteRepository> { RatesRestRepository(get()) }
        factory<RatesRepository> { RatesRepositoryImpl(get(), get()) }

        factory<RatesService> {
            get<Retrofit>().create(RatesService::class.java)
        }
    }
}