package com.tahadardev.exchangerate.feature.currency_exchange.data.repository

import com.tahadardev.exchangerate.common.Resource
import com.tahadardev.exchangerate.feature.currency_exchange.domain.model.Currency
import com.tahadardev.exchangerate.feature.currency_exchange.domain.model.CurrencyRate
import com.tahadardev.exchangerate.feature.currency_exchange.domain.repository.CurrencyRepository
import com.tahadardev.exchangerate.feature.currency_exchange.domain.util.Utils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class FakeCurrencyRepository() : CurrencyRepository {

    private val currencies = listOf<Currency>(
        Currency("AED", "United Arab Emirates Dirham"),
        Currency("BTC", "Bitcoin"),
        Currency("EUR", "Euro"),
        Currency("USD", "United States Dollar"),
        Currency("ZWL", "Zimbabwean Dollar")
    )

    private val currencyExchangeRates = listOf<CurrencyRate>(
        CurrencyRate("AED", 3.672975),
        CurrencyRate("BTC", 0.000038359186),
        CurrencyRate("EUR", 0.935125),
        CurrencyRate("USD", 1.0),
        CurrencyRate("ZWL", 322.0)
    )

    override suspend fun fetchCurrencies(): Flow<Resource<List<Currency>>> {
        return flow { emit(Resource.Success(currencies)) }
    }

    override suspend fun fetchExchangeRates(lastExchangeRatesTimeStamp: Long): Flow<Resource<List<CurrencyRate>>> {
        return flow {

            val isThresholdCrossed =
                Utils.isExchangeRatesTimeStampThresholdCrossed(lastExchangeRatesTimeStamp)

            val responseMsg = if (isThresholdCrossed) {
                "Fetched from Server"
            } else {
                "Fetched from local db"
            }
            emit(Resource.Success(currencyExchangeRates, responseMsg))
        }
    }
}