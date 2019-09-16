package com.raimbekov.rates.main.domain

import com.raimbekov.rates.main.domain.model.Rate
import io.reactivex.Flowable

class GetRatesUseCase(
    private val ratesRepository: RatesRepository
) {

    fun getRates(currency: String, amount: Double): Flowable<List<Rate>> =
        ratesRepository.getRates(currency)
            .map { rates ->
                rates.map { it.copy(value = it.value * amount) }
            }
}