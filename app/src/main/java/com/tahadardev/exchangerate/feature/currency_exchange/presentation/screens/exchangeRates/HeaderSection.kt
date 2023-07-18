package com.tahadardev.exchangerate.feature.currency_exchange.presentation.screens.exchangeRates

import MaskVisualTransformation
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.tahadardev.exchangerate.R
import com.tahadardev.exchangerate.feature.currency_exchange.domain.model.Currency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderSection(
    selectedCurrency: String,
    userQuery: String,
    currencies: List<Currency>,
    onSearchValueChanged: (searchValue: String) -> Unit = {},
    onCurrencySelected: (currency: Currency) -> Unit = {}
) {
    var isDropDownExpanded by remember {
        mutableStateOf(false)
    }

    val dropdownIcon = if (isDropDownExpanded) {
        Icons.Default.KeyboardArrowUp
    } else {
        Icons.Default.KeyboardArrowDown
    }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {


        OutlinedTextField(
            placeholder = { Text(stringResource(id = R.string.enter_value)) },
            value = userQuery,
            maxLines = 1,
            onValueChange = {
                if (it.isDigitsOnly()) {
                        onSearchValueChanged(it)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = MaskVisualTransformation(showZeroValue = true),
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
        )

        Spacer(Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .align(Alignment.End)
                .fillMaxWidth(.3f)
        ) {
            OutlinedTextField(
                value = selectedCurrency,
                onValueChange = {},
                enabled = false,
                trailingIcon = {
                    Icon(
                        imageVector = dropdownIcon,
                        contentDescription = ""
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    disabledTrailingIconColor = Color.Black,
                    disabledTextColor = Color.Black,
                    disabledBorderColor = MaterialTheme.colorScheme.outline
                ),
                modifier = Modifier
                    .clickable {
                        isDropDownExpanded = !isDropDownExpanded
                    }
            )
            DropdownMenu(
                expanded = isDropDownExpanded,
                onDismissRequest = { isDropDownExpanded = false }) {
                for (currency in currencies) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                currency.currency,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        },
                        onClick = {
                            onCurrencySelected(currency)
                            isDropDownExpanded = false
                        }, modifier = Modifier.background(Color.White)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderSectionPrev() {
    HeaderSection("USD", "1", listOf(Currency("USD", "US Dollar")))
}