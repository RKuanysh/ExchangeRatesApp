package com.raimbekov.rates.main.repository

import com.raimbekov.rates.main.domain.RatesLocalRepository
import com.raimbekov.rates.main.domain.model.Rate
import java.util.concurrent.CopyOnWriteArrayList

class RatesInMemoryRepository() : RatesLocalRepository {

    private var rates: CopyOnWriteArrayList<Rate>? = null

    override fun getRates(): List<Rate> = rates ?: emptyList()

    override fun setRates(rates: List<Rate>) {
        this.rates = CopyOnWriteArrayList(rates)
    }
}