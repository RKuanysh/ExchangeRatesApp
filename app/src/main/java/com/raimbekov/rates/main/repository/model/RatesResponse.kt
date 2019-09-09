package com.raimbekov.rates.main.repository.model

import com.google.gson.annotations.SerializedName

data class RatesResponse(

    @SerializedName("base")
    val base: String,

    @SerializedName("rates")
    val rates: HashMap<String, Double>
)