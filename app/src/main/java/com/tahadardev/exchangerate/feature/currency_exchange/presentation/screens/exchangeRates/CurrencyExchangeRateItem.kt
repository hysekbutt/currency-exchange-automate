package com.tahadardev.exchangerate.feature.currency_exchange.presentation.screens.exchangeRates

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CurrencyExchangeRateItem(currency : String, rate: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = currency, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(text = rate, color = MaterialTheme.colorScheme.primary, maxLines = 1)
    }
}

@Preview(showBackground = true)
@Composable
fun CurrencyExchangeRateItemPrev() {
    CurrencyExchangeRateItem("USD",  "0.1123")
}