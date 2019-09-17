package com.raimbekov.rates.main.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raimbekov.rates.common.SingleLiveEvent
import com.raimbekov.rates.main.domain.GetRatesUseCase
import com.raimbekov.rates.main.view.model.RateViewData
import com.raimbekov.rates.main.view.model.RatesUpdateData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.text.DecimalFormat

class RatesViewModel(
    private val getRatesUseCase: GetRatesUseCase
) : ViewModel() {

    val ratesLiveData = MutableLiveData<RatesUpdateData>()
    val ratesError = SingleLiveEvent<Unit>()

    private var ratesSubscription: Disposable? = null

    var currency: String = "EUR"
        get
        private set

    private var amount: Double = 1.0

    init {
    }

    fun setCurrency(rate: RateViewData) {
        if (currency == rate.currency) {
            return
        }
        ratesLiveData.value = RatesUpdateData(listOf(rate), true)

        this.currency = rate.currency
        this.amount = rate.value.toDouble()
        update()
    }

    fun setAmount(amount: String) {
        this.amount = amount.toDoubleOrNull() ?: 0.0
        update()
    }

    fun retry() {
        update()
    }

    private fun update() {
        ratesSubscription?.dispose()

        ratesSubscription = getRatesUseCase.getRates(currency, amount)
            .observeOn(Schedulers.single())
            .map {
                it.map {
                    RateViewData(it.currency, formatter.get()?.format(it.value) ?: "0.0")
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                ratesLiveData.value = RatesUpdateData(listOf(RateViewData(currency, amount.toString())) + it)
            }, {
                Log.e(javaClass.simpleName, it.message)
                ratesError.value = Unit
            })
    }

    fun start() {
        update()
    }

    fun stop() {
        ratesSubscription?.dispose()
    }

    companion object {
        val formatter = object : ThreadLocal<DecimalFormat>() {
            override fun initialValue(): DecimalFormat = DecimalFormat("#0.####")
        }
    }
}