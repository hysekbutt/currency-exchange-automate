package com.tahadardev.exchangerate.feature.currency_exchange.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tahadardev.exchangerate.feature.currency_exchange.domain.model.Currency

@Entity
data class CurrencyEntity(
    val currency : String,
    val name: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)

fun CurrencyEntity.toCurrency() : Currency {
    return Currency(currency, name)
}

