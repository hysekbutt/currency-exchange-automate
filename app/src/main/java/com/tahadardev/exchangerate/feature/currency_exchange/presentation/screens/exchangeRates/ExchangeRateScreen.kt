package com.tahadardev.exchangerate.feature.currency_exchange.presentation.screens.exchangeRates

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tahadardev.exchangerate.feature.currency_exchange.domain.util.Utils
import com.tahadardev.exchangerate.feature.currency_exchange.presentation.CurrencyViewModel
import com.tahadardev.exchangerate.feature.currency_exchange.presentation.ui.theme.ExchangeRateTheme

@Composable
fun ExchangeRateScreen(
    viewModel: CurrencyViewModel = hiltViewModel()
) {
    val state = viewModel.currencyExchangeState.value
    val currencies = state.currencyList
    val exchangeRates = state.exchangeRates
    var selectedCurrency by remember { mutableStateOf(state.selectedCurrency) }
    var userQuery by remember { mutableStateOf(state.userQuery) }

    Box(modifier = Modifier.fillMaxSize()) {
        if (currencies.isNotEmpty() && exchangeRates.isNotEmpty()) {
            LazyColumn {
                item {
                    HeaderSection(
                        selectedCurrency,
                        userQuery,
                        currencies,
                        {
                            userQuery = it
                            viewModel.onUserValueChanged(it)
                        },
                        {
                            selectedCurrency = it.currency
                            viewModel.updateExchangeRates(selectedCurrency)
                        })

                    Divider()
                }

                items(exchangeRates.size) { index ->
                    val currencyRate = exchangeRates[index]
                    val symbol = currencyRate.symbol
                    val rate = currencyRate.rate
                    val convertedRate = Utils.getConversionRate(
                        //the formatting is done to handle the masking applied on the transformed text
                        if (userQuery.isBlank()) "1" else "%2f".format(userQuery.toDouble() / 100),
                        rate
                    )
                    CurrencyExchangeRateItem(symbol, convertedRate)
                }
            }
        } else if (state.errorMsg.isNotBlank()) {
            Text(
                text = state.errorMsg,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .align(Alignment.Center)
            )
        } else if (state.isLoading) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExchangeRatePrev() {
    ExchangeRateTheme {
        ExchangeRateScreen()
    }
}