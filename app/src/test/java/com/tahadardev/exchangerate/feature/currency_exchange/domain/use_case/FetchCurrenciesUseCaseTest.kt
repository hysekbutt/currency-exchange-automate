package com.tahadardev.exchangerate.feature.currency_exchange.domain.use_case

import com.tahadardev.exchangerate.common.Resource
import com.tahadardev.exchangerate.feature.currency_exchange.data.repository.FakeCurrencyRepository
import com.tahadardev.exchangerate.feature.currency_exchange.domain.model.Currency
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FetchCurrenciesUseCaseTest {

    private lateinit var repo : FakeCurrencyRepository

    @Before
    fun setup() {
        repo = FakeCurrencyRepository()
    }

    @Test
    fun fetchCurrenciesShouldReturnListOfCurrency() {
        var currencyList : List<Currency>? = listOf()
        runTest {
            try {
                repo.fetchCurrencies().collect{
                    currencyList = when (it) {
                        is Resource.Success -> it.data
                        else -> {
                            emptyList()
                        }
                    }
                }
            } catch (e: Exception) {
                currencyList = null
            }
        }
         assertTrue(currencyList?.isNotEmpty() == true && currencyList?.toTypedArray()?.isArrayOf<Currency>() == true)
    }
}