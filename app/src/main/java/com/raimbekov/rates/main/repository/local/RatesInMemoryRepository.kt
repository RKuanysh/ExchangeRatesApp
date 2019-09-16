package com.raimbekov.rates.main.repository.local

import com.raimbekov.rates.main.domain.model.Rate
import java.util.concurrent.CopyOnWriteArrayList

class RatesInMemoryRepository() : RatesLocalRepository {

    private var currency: String? = null
    private var rates: CopyOnWriteArrayList<Rate>? = null

    @Synchronized
    override fun getRates(currency: String): List<Rate> =
        if (this.currency == currency) {
            rates ?: emptyList()
        } else {
            emptyList()
        }

    @Synchronized
    override fun setRates(currency: String, rates: List<Rate>) {
        this.currency = currency
        this.rates = CopyOnWriteArrayList(rates)
    }
}