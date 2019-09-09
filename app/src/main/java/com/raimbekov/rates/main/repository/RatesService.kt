package com.raimbekov.rates.main.repository

import com.raimbekov.rates.main.repository.model.RatesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RatesService {

    @GET("latest")
    fun getRates(
        @Query("base") base: String
    ): Single<RatesResponse>
}