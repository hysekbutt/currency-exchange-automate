package com.tahadardev.exchangerate.feature.currency_exchange.data.remote.dto

import com.tahadardev.exchangerate.feature.currency_exchange.data.local.entity.CurrencyRateEntity

data class ExchangeRateDto(
    val base: String,
    val disclaimer: String,
    val license: String,
    val rates: MutableMap<String, Double>,
    val timestamp: Int
)

fun Map.Entry<String, Double>.toCurrencyRateEntity(): CurrencyRateEntity {
    return CurrencyRateEntity(key, value)
}