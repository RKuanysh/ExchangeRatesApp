package com.raimbekov.rates.main.domain

import com.raimbekov.rates.main.domain.model.Rate
import io.reactivex.Flowable

interface RatesRepository {

    fun getRates(baseCurrency: String): Flowable<List<Rate>>
}