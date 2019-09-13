package com.raimbekov.rates.main.view.model

import com.raimbekov.rates.main.domain.model.Rate

data class RatesUpdateData(
    val rates: List<Rate>,
    val isFirstChanged: Boolean = false
)