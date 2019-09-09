package com.raimbekov.rates.main.repository

import com.raimbekov.rates.main.domain.RatesRepository
import com.raimbekov.rates.main.domain.model.Rate
import io.reactivex.Single

class RatesRemoteRepository(
    private val ratesService: RatesService
) : RatesRepository {

    override fun getRates(base: String): Single<List<Rate>> =
        ratesService.getRates(base)
            .map {
                val rates = mutableListOf<Rate>()
                it.rates.keys.forEach { key ->
                    rates.add(Rate(key, it.rates.get(key) ?: 0.0))
                }
                rates
            }

}