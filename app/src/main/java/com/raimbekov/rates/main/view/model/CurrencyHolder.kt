package com.raimbekov.rates.main.view.model

import com.raimbekov.rates.R

object CurrencyHolder {

    private val currencies = HashMap<String, CurrencyData>().apply {
        put("AUD", CurrencyData(R.mipmap.flag_australia, R.string.currency_australia))
        put("BGN", CurrencyData(R.mipmap.flag_bulgaria, R.string.currency_bulgaria))
        put("BRL", CurrencyData(R.mipmap.flag_brazil, R.string.currency_brazil))
        put("CAD", CurrencyData(R.mipmap.flag_canada, R.string.currency_canada))
        put("CHF", CurrencyData(R.mipmap.flag_switzerland, R.string.currency_switzerland))
        put("CNY", CurrencyData(R.mipmap.flag_china, R.string.currency_china))
        put("CZK", CurrencyData(R.mipmap.flag_czechia, R.string.currency_czechia))
        put("DKK", CurrencyData(R.mipmap.flag_denmark, R.string.currency_denmark))
        put("GBP", CurrencyData(R.mipmap.flag_britain, R.string.currency_britain))
        put("HKD", CurrencyData(R.mipmap.flag_hong_kong, R.string.currency_hong_kong))
        put("HRK", CurrencyData(R.mipmap.flag_croatia, R.string.currency_croatia))
        put("HUF", CurrencyData(R.mipmap.flag_hungaria, R.string.currency_hungaria))
        put("IDR", CurrencyData(R.mipmap.flag_indonesia, R.string.currency_indonesia))
        put("ILS", CurrencyData(R.mipmap.flag_israel, R.string.currency_israel))
        put("INR", CurrencyData(R.mipmap.flag_india, R.string.currency_india))
        put("ISK", CurrencyData(R.mipmap.flag_iceland, R.string.currency_iceland))
        put("JPY", CurrencyData(R.mipmap.flag_japan, R.string.currency_japan))
        put("KRW", CurrencyData(R.mipmap.flag_south_korea, R.string.currency_south_korea))
        put("MXN", CurrencyData(R.mipmap.flag_mexico, R.string.currency_mexico))
        put("MYR", CurrencyData(R.mipmap.flag_malaysia, R.string.currency_malaysia))
        put("NOK", CurrencyData(R.mipmap.flag_norway, R.string.currency_norway))
        put("NZD", CurrencyData(R.mipmap.flag_new_zealand, R.string.currency_new_zealand))
        put("PHP", CurrencyData(R.mipmap.flag_philippines, R.string.currency_philippines))
        put("PLN", CurrencyData(R.mipmap.flag_poland, R.string.currency_poland))
        put("RON", CurrencyData(R.mipmap.flag_romania, R.string.currency_romania))
        put("RUB", CurrencyData(R.mipmap.flag_russia, R.string.currency_russia))
        put("SEK", CurrencyData(R.mipmap.flag_sweden, R.string.currency_sweden))
        put("SGD", CurrencyData(R.mipmap.flag_singapore, R.string.currency_singapore))
        put("THB", CurrencyData(R.mipmap.flag_thailand, R.string.currency_thailand))
        put("TRY", CurrencyData(R.mipmap.flag_turkey, R.string.currency_turkey))
        put("USD", CurrencyData(R.mipmap.flag_usa, R.string.currency_usa))
        put("ZAR", CurrencyData(R.mipmap.flag_south_africa, R.string.currency_south_africa))

        put("EUR", CurrencyData(R.mipmap.flag_europe, R.string.currency_europe))
    }

    fun getCurrency(currency: String): CurrencyData =
        currencies.get(currency)!!
}