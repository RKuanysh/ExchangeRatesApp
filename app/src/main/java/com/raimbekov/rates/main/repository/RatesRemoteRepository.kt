package com.raimbekov.rates.main.repository

import android.util.Log
import com.raimbekov.rates.main.domain.RatesRepository
import com.raimbekov.rates.main.domain.model.Rate
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

class RatesRemoteRepository(
    private val ratesService: RatesService
) : RatesRepository {

    override fun getRates(baseCurrency: String): Flowable<List<Rate>> =
        Flowable.interval(1, TimeUnit.SECONDS)
            .switchMapSingle {
                Log.d(javaClass.name, "requesting: " + baseCurrency)
                ratesService.getRates(baseCurrency)
            }
            .map { ratesResponse ->
                val rates = ArrayList<Rate>(ratesResponse.rates.size)
                ratesResponse.rates.entries.forEachIndexed { index, entry ->
                    rates.add(index, Rate(entry.key, entry.value))
                }
                rates
            }
}