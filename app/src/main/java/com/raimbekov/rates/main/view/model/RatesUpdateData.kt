package com.raimbekov.rates.main.view.model

data class RatesUpdateData(
    val rates: List<RateViewData>,
    val isFirstChanged: Boolean = false
)