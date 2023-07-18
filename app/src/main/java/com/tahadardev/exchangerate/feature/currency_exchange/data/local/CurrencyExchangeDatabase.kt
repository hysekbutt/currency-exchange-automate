package com.tahadardev.exchangerate.feature.currency_exchange.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tahadardev.exchangerate.feature.currency_exchange.data.local.entity.CurrencyEntity
import com.tahadardev.exchangerate.feature.currency_exchange.data.local.entity.CurrencyRateEntity

@Database(
    entities = [CurrencyEntity::class, CurrencyRateEntity::class],
    version = 1
)
abstract class CurrencyExchangeDatabase : RoomDatabase() {
    abstract val currencyDao: CurrencyDao
    abstract val currencyRateDao: CurrencyRateDao
}