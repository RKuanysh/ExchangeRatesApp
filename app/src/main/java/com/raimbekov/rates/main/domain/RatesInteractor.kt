package com.raimbekov.rates.main.domain

import com.raimbekov.rates.main.domain.model.Rate
import io.reactivex.Single

class RatesInteractor(
    private val ratesRepository: RatesRepository
) {

    fun getRates(base: String): Single<List<Rate>> =
        ratesRepository.getRates(base)
}