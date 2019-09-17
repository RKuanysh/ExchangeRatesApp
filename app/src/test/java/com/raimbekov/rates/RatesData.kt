package com.raimbekov.rates

import com.raimbekov.rates.main.domain.model.Rate

object RatesData {

    val rates: List<Rate> = mutableListOf(
        Rate("AUD", 1.3),
        Rate("USD", 1.2),
        Rate("GBP", 0.9)
    )
}