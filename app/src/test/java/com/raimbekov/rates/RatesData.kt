package com.raimbekov.rates

import com.raimbekov.rates.main.domain.model.Rate

object RatesData {

    val rates: List<Rate> = mutableListOf<Rate>()
        .apply {
            add(Rate("AUD", 1.3))
            add(Rate("USD", 1.2))
            add(Rate("GBP", 0.9))
        }

}