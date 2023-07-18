package com.tahadardev.exchangerate.feature.currency_exchange.domain.use_case

import com.tahadardev.exchangerate.common.Resource
import com.tahadardev.exchangerate.feature.currency_exchange.domain.model.CurrencyRate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateExchangeRatesUseCase @Inject constructor() {
    operator fun invoke(
        exchangeRates: List<CurrencyRate>,
        selectedCurrency: String
    ): Flow<Resource<List<CurrencyRate>>> {
        return flow {
            try {
                /*
                 *  x = 2y;
                 *  x = 3z;
                 *  y = 3z / 2
                 */
                emit(Resource.Loading())
                val selectedCurrencyRate =
                    exchangeRates.first { it.symbol == selectedCurrency }.rate
                exchangeRates.forEach { it.rate = it.rate / selectedCurrencyRate }
                emit(Resource.Success(exchangeRates))
            } catch (e: Exception) {
                emit(Resource.Error(message = "Unable to convert the exchange rates"))
            }
        }
    }
}