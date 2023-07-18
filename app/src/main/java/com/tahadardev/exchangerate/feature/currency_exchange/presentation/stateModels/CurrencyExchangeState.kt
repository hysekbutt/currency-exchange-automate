package com.tahadardev.exchangerate.feature.currency_exchange.presentation.stateModels

import com.tahadardev.exchangerate.feature.currency_exchange.domain.model.Currency
import com.tahadardev.exchangerate.feature.currency_exchange.domain.model.CurrencyRate

data class CurrencyExchangeState(
    var currencyList: List<Currency> = emptyList(),
    var exchangeRates: List<CurrencyRate> = emptyList(),
    var isLoading: Boolean = false,
    var errorMsg: String = "",
    var selectedCurrency: String = "USD",
    var userQuery: String = ""
)