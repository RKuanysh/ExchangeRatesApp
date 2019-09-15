package com.raimbekov.rates.main.domain

import com.raimbekov.rates.main.domain.model.Rate
import io.reactivex.Flowable

interface RatesRemoteRepository {
    fun setCurrency(currency: String)
    fun getRates(): Flowable<List<Rate>>
}