package com.raimbekov.rates.main.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raimbekov.rates.common.SingleLiveEvent
import com.raimbekov.rates.main.domain.RatesInteractor
import com.raimbekov.rates.main.domain.model.Rate
import com.raimbekov.rates.main.view.model.RatesUpdateData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class MainViewModel(
    private val ratesInteractor: RatesInteractor
) : ViewModel() {

    val ratesLiveData = MutableLiveData<RatesUpdateData>()
    val ratesError = SingleLiveEvent<Unit>()
    val loading = MutableLiveData<Boolean>().apply { value = false }

    private var ratesSubscription: Disposable? = null

    private var currency: String = "EUR"
    private var amount: Double = 1.0

    init {
        ratesInteractor.setCurrency(currency)
        update()
    }

    fun setCurrency(newBaseCurrencyIndex: Int, rate: Rate) {
        var ratesUpdateData = ratesLiveData.value
        if (rate.currency != currency && ratesUpdateData != null) {
            // swap new currency with base
            Collections.swap(ratesUpdateData.rates, 0, newBaseCurrencyIndex)
            ratesUpdateData = ratesUpdateData.copy(isFirstChanged = true)
            ratesLiveData.value = ratesUpdateData

            this.currency = rate.currency
            this.amount = rate.value
            ratesInteractor.setCurrency(currency)
            update()
        }
    }

    fun setAmount(amount: Double) {
        this.amount = amount
        update()
    }

    private fun update() {
        ratesSubscription?.dispose()

        ratesSubscription = ratesInteractor.getRates(amount)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                loading.value = true
            }
            .subscribe({
                loading.value = false
                ratesLiveData.value = RatesUpdateData(listOf(Rate(currency, amount)) + it)
            }, {
                it.printStackTrace()
            })
    }

    override fun onCleared() {
        super.onCleared()
        ratesSubscription?.dispose()
    }
}