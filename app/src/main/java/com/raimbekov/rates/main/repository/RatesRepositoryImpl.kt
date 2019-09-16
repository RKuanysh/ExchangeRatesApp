package com.raimbekov.rates.main.repository

import com.raimbekov.rates.main.domain.RatesRepository
import com.raimbekov.rates.main.domain.model.Rate
import com.raimbekov.rates.main.repository.local.RatesLocalRepository
import com.raimbekov.rates.main.repository.remote.RatesRemoteRepository
import io.reactivex.Flowable

class RatesRepositoryImpl(
    private val ratesRemoteRepository: RatesRemoteRepository,
    private val ratesLocalRepository: RatesLocalRepository
) : RatesRepository {

    override fun getRates(currency: String): Flowable<List<Rate>> =
        ratesRemoteRepository.getRates(currency)
            .doOnNext { ratesLocalRepository.setRates(currency, it) }
            .startWith(ratesLocalRepository.getRates(currency))

}