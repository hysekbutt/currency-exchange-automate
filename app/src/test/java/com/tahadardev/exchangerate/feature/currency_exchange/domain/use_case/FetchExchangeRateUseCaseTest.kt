package com.tahadardev.exchangerate.feature.currency_exchange.domain.use_case

import com.tahadardev.exchangerate.common.Constants
import com.tahadardev.exchangerate.common.Resource
import com.tahadardev.exchangerate.feature.currency_exchange.data.repository.FakeCurrencyRepository
import com.tahadardev.exchangerate.feature.currency_exchange.domain.model.Currency
import com.tahadardev.exchangerate.feature.currency_exchange.domain.model.CurrencyRate
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FetchExchangeRateUseCaseTest {
    private lateinit var repo : FakeCurrencyRepository

    @Before
    fun setup() {
        repo = FakeCurrencyRepository()
    }

    @Test
    fun fetchCurrenciesShouldReturnListOfCurrencyRate() {
        var currencyRates : List<CurrencyRate>? = listOf()
        runTest {
            try {
                repo.fetchExchangeRates(System.currentTimeMillis()).collect{
                    currencyRates = when (it) {
                        is Resource.Success -> it.data
                        else -> {
                            emptyList()
                        }
                    }
                }
            } catch (e: Exception) {
                currencyRates = null
            }
        }
        assertTrue(currencyRates?.isNotEmpty() == true && currencyRates?.toTypedArray()?.isArrayOf<CurrencyRate>() == true)
    }

    @Test
    fun fetchCurrenciesShouldFetchFromServerIfTimestampHasCrossedThreshold() {
        var responseMsg :String = ""
        runTest {
            try {
                val savedExchangeRateTimestamp = System.currentTimeMillis() - Constants.EXCHANGE_RATE_UPDATE_THRESHOLD
                repo.fetchExchangeRates(savedExchangeRateTimestamp).collect{
                    responseMsg = when (it) {
                        is Resource.Success -> it.message!!
                        else -> {
                            ""
                        }
                    }
                }
            } catch (e: Exception) {
                responseMsg = ""
            }
        }

        assertTrue(responseMsg == "Fetched from Server")
    }

    @Test
    fun fetchCurrenciesShouldFetchFromDbIfTimestampHasNotCrossedThreshold() {
        var responseMsg :String = ""
        runTest {
            try {
                val savedExchangeRateTimestamp = System.currentTimeMillis()
                repo.fetchExchangeRates(savedExchangeRateTimestamp).collect{
                    responseMsg = when (it) {
                        is Resource.Success -> it.message!!
                        else -> {
                            ""
                        }
                    }
                }
            } catch (e: Exception) {
                responseMsg = ""
            }
        }

        assertTrue(responseMsg == "Fetched from local db")
    }
}