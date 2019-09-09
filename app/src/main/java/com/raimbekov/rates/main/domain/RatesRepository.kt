package com.raimbekov.rates.main.domain

import com.raimbekov.rates.main.domain.model.Rate
import io.reactivex.Single

interface RatesRepository {

    fun getRates(base: String): Single<List<Rate>>
}