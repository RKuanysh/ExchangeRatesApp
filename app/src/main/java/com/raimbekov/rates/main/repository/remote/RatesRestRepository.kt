package com.raimbekov.rates.main.repository.remote

import android.util.Log
import com.raimbekov.rates.main.domain.model.Rate
import com.raimbekov.rates.main.repository.RatesService
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

class RatesRestRepository(
    private val ratesService: RatesService
) : RatesRemoteRepository {

    override fun getRates(currency: String): Flowable<List<Rate>> =
        Flowable
            .interval(1, TimeUnit.SECONDS)
            .switchMapSingle {
                Log.d(javaClass.simpleName, "requesting $currency")
                ratesService.getRates(currency)
            }
            .map<List<Rate>> { ratesResponse ->
                val rates = ArrayList<Rate>(ratesResponse.rates.size)
                ratesResponse.rates.entries.forEachIndexed { index, entry ->
                    rates.add(index, Rate(entry.key, entry.value))
                }
                rates
            }
}