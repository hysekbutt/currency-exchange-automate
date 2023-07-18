package com.tahadardev.exchangerate.feature.currency_exchange.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tahadardev.exchangerate.feature.currency_exchange.domain.model.CurrencyRate

@Entity
data class CurrencyRateEntity(
    var currency: String,
    var exchangeRate: Double,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

fun CurrencyRateEntity.toCurrencyRate(): CurrencyRate {
    return CurrencyRate(currency, exchangeRate)
}