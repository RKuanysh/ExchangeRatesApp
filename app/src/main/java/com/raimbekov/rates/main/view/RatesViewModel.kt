package com.raimbekov.rates.main.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raimbekov.rates.common.SingleLiveEvent
import com.raimbekov.rates.main.domain.RatesInteractor
import com.raimbekov.rates.main.view.model.RateViewData
import com.raimbekov.rates.main.view.model.RatesUpdateData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.text.DecimalFormat

class RatesViewModel(
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

    fun setCurrency(rate: RateViewData) {
        if (currency == rate.currency) {
            return
        }
        ratesLiveData.value = RatesUpdateData(listOf(rate), true)

        this.currency = rate.currency
        this.amount = rate.value.toDouble()
        ratesInteractor.setCurrency(rate.currency)
        update()
    }

    fun setAmount(amount: String) {
        this.amount = amount.toDoubleOrNull() ?: 0.0
        update()
    }

    override fun onCleared() {
        super.onCleared()
        ratesSubscription?.dispose()
    }

    fun retry() {
        update()
    }

    private fun update() {
        ratesSubscription?.dispose()

        ratesSubscription = ratesInteractor.getRates(amount)
            .observeOn(Schedulers.single())
            .map {
                it.map {
                    RateViewData(it.currency, formatter.get()?.format(it.value) ?: "0.0")
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                loading.value = true
            }
            .doOnEach {
                loading.value = false
            }
            .subscribe({
                ratesLiveData.value = RatesUpdateData(listOf(RateViewData(currency, amount.toString())) + it, false)
            }, {
                it.printStackTrace()
                loading.value = false
                ratesError.value = Unit
            })
    }

    companion object {
        val formatter = object : ThreadLocal<DecimalFormat>() {
            override fun initialValue(): DecimalFormat = DecimalFormat("#0.####")
        }
    }
}