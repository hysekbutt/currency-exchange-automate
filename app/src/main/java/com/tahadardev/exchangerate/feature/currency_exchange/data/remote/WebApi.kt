package com.tahadardev.exchangerate.feature.currency_exchange.data.remote

import com.tahadardev.exchangerate.BuildConfig.API_KEY
import com.tahadardev.exchangerate.feature.currency_exchange.data.remote.dto.ExchangeRateDto
import retrofit2.Response
import retrofit2.http.GET

interface WebApi {

    @GET("/currencies.json")
    suspend fun fetchCurrencies() : Response<MutableMap<String , String>>

    @GET("https://openexchangerates.org/api/latest.json?app_id=${API_KEY}&base=USD")
    suspend fun fetchExchangeRates(): Response<ExchangeRateDto>
}