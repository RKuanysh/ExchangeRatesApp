package com.raimbekov.rates.main.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raimbekov.rates.common.SingleLiveEvent
import com.raimbekov.rates.main.domain.RatesInteractor
import com.raimbekov.rates.main.domain.model.Rate
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    ratesInteractor: RatesInteractor
) : ViewModel() {

    val ratesLiveData = MutableLiveData<List<Rate>>()
    val ratesError = SingleLiveEvent<Unit>()

    private val ratesSubscription: Disposable

    init {

        ratesSubscription = ratesInteractor.getRates("EUR")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { rates ->
                    ratesLiveData.value = rates
                }, {
                    it.printStackTrace()
                    ratesError.value = Unit
                })
    }

    override fun onCleared() {
        super.onCleared()
        ratesSubscription.dispose()
    }
}