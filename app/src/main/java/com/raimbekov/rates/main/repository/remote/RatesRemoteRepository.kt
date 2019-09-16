package com.raimbekov.rates.main.repository.remote

import com.raimbekov.rates.main.domain.model.Rate
import io.reactivex.Flowable

interface RatesRemoteRepository {
    fun getRates(currency: String): Flowable<List<Rate>>
}