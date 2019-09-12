package com.raimbekov.rates.main.domain

import com.raimbekov.rates.main.domain.model.Rate
import io.reactivex.Flowable

class GetRatesUseCase(
    private val repository: RatesRepository
) {

    fun getRates(baseCurrency: String, amount: Double = 1.0): Flowable<List<Rate>> =
        repository.getRates(baseCurrency)
            .map { rates ->
                rates.map { it.copy(value = it.value * amount) }
            }
}