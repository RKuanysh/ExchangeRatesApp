package com.raimbekov.rates.main.domain

import com.raimbekov.rates.main.domain.model.Rate

interface RatesLocalRepository {
    fun getRates(): List<Rate>
    fun setRates(rates: List<Rate>)
}