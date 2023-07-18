package com.tahadardev.exchangerate.feature.currency_exchange.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tahadardev.exchangerate.feature.currency_exchange.data.local.entity.CurrencyRateEntity

@Dao
interface CurrencyRateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencyRates(currencyRates: List<CurrencyRateEntity>)

    @Delete
    suspend fun removeCurrencyRates(currencyRates: List<CurrencyRateEntity>)

    @Query("SELECT * FROM CurrencyRateEntity")
    suspend fun getCurrencyRates(): List<CurrencyRateEntity>
}