package com.raimbekov.rates.main.repository

import android.util.Log
import com.raimbekov.rates.main.domain.RatesRemoteRepository
import com.raimbekov.rates.main.domain.model.Rate
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.rxkotlin.Flowables
import java.util.concurrent.TimeUnit

class RatesRestRepository(
    private val ratesService: RatesService
) : RatesRemoteRepository {

    private val currencyProcessor = BehaviorProcessor.create<String>()

    override fun setCurrency(currency: String) {
        currencyProcessor.offer(currency)
    }

    override fun getRates(): Flowable<List<Rate>> =
        Flowables.combineLatest(
            Flowable.interval(1, TimeUnit.SECONDS),
            currencyProcessor.distinctUntilChanged()
        ).switchMapSingle { (_, baseCurrency) ->
            Log.d(javaClass.name, "requesting: " + baseCurrency)
            ratesService.getRates(baseCurrency)
        }
            .map<List<Rate>> { ratesResponse ->
                val rates = ArrayList<Rate>(ratesResponse.rates.size)
                ratesResponse.rates.entries.forEachIndexed { index, entry ->
                    rates.add(index, Rate(entry.key, entry.value))
                }
                rates
            }
}