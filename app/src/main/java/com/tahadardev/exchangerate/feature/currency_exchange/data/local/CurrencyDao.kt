package com.tahadardev.exchangerate.feature.currency_exchange.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tahadardev.exchangerate.feature.currency_exchange.data.local.entity.CurrencyEntity
import com.tahadardev.exchangerate.feature.currency_exchange.data.local.entity.CurrencyRateEntity

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencies(currencies : List<CurrencyEntity>)

    @Delete
    suspend fun removeCurrencies(currencies: List<CurrencyEntity>)

    @Query("SELECT * FROM CurrencyEntity")
    suspend fun getCurrencies() : List<CurrencyEntity>
}