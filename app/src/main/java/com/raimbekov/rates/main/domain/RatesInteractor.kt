package com.raimbekov.rates.main.domain

import com.raimbekov.rates.main.domain.model.Rate
import io.reactivex.Flowable

class RatesInteractor(
    private val ratesRemoteRepository: RatesRemoteRepository,
    private val ratesLocalRepository: RatesLocalRepository
) {

    fun setCurrency(currency: String) {
        ratesLocalRepository.setRates(emptyList())
        ratesRemoteRepository.setCurrency(currency)
    }

    fun getRates(amount: Double): Flowable<List<Rate>> =
        ratesRemoteRepository.getRates()
            .doOnNext { ratesLocalRepository.setRates(it) }
            .doOnError { ratesLocalRepository.setRates(emptyList()) }
            .startWith(ratesLocalRepository.getRates())
            .map { rates ->
                rates.map { it.copy(value = it.value * amount) }
            }
}