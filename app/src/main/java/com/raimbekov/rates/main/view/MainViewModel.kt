package com.raimbekov.rates.main.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raimbekov.rates.common.SingleLiveEvent
import com.raimbekov.rates.main.domain.GetRatesUseCase
import com.raimbekov.rates.main.domain.model.Rate
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    private val getRatesUseCase: GetRatesUseCase
) : ViewModel() {

    val ratesLiveData = MutableLiveData<List<Rate>>()
    val ratesError = SingleLiveEvent<Unit>()
    val loading = MutableLiveData<Boolean>().apply { value = false }

    private var ratesSubscription: Disposable? = null

    private var currency: String = "EUR"
    private var amount: Double = 1.0

    init {
        update()
    }

    fun setCurrency(rate: Rate) {
        this.currency = rate.currency
        this.amount = rate.value
        update()
    }

    fun setAmount(amount: Double) {
        this.amount = amount
        update()
    }

    private fun update() {
        ratesSubscription?.dispose()

        ratesSubscription = getRatesUseCase.getRates(currency, amount)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                loading.value = true
            }
            .subscribe({
                loading.value = false
                ratesLiveData.value = listOf(Rate(currency, amount)) + it
            }, {
                it.printStackTrace()
            })
    }

    override fun onCleared() {
        super.onCleared()
        ratesSubscription?.dispose()
    }
}