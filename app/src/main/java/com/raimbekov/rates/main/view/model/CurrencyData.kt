package com.raimbekov.rates.main.view.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class CurrencyData(
    @DrawableRes
    val flagResId: Int,

    @StringRes
    val nameResId: Int
)