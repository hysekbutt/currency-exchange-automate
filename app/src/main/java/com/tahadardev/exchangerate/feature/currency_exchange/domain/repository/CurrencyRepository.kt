package com.tahadardev.exchangerate.feature.currency_exchange.domain.repository

import com.tahadardev.exchangerate.common.Resource
import com.tahadardev.exchangerate.feature.currency_exchange.domain.model.Currency
import com.tahadardev.exchangerate.feature.currency_exchange.domain.model.CurrencyRate
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {

    suspend fun fetchCurrencies(): Flow<Resource<List<Currency>>>

    suspend fun fetchExchangeRates(lastExchangeRatesTimeStamp : Long): Flow<Resource<List<CurrencyRate>>>
}