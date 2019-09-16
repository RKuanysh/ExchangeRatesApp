package com.raimbekov.rates.main.repository.local

import com.raimbekov.rates.main.domain.model.Rate

interface RatesLocalRepository {
    fun getRates(currency: String): List<Rate>
    fun setRates(currency: String, rates: List<Rate>)
}