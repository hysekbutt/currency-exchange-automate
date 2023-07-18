package com.tahadardev.exchangerate.feature.currency_exchange.data.repository

import android.content.Context
import com.tahadardev.exchangerate.R
import com.tahadardev.exchangerate.common.DataStorePreferences
import com.tahadardev.exchangerate.common.Resource
import com.tahadardev.exchangerate.feature.currency_exchange.data.local.CurrencyDao
import com.tahadardev.exchangerate.feature.currency_exchange.data.local.CurrencyRateDao
import com.tahadardev.exchangerate.feature.currency_exchange.data.local.entity.CurrencyEntity
import com.tahadardev.exchangerate.feature.currency_exchange.data.local.entity.toCurrency
import com.tahadardev.exchangerate.feature.currency_exchange.data.local.entity.toCurrencyRate
import com.tahadardev.exchangerate.feature.currency_exchange.data.remote.WebApi
import com.tahadardev.exchangerate.feature.currency_exchange.data.remote.dto.toCurrencyRateEntity
import com.tahadardev.exchangerate.feature.currency_exchange.domain.model.Currency
import com.tahadardev.exchangerate.feature.currency_exchange.domain.model.CurrencyRate
import com.tahadardev.exchangerate.feature.currency_exchange.domain.repository.CurrencyRepository
import com.tahadardev.exchangerate.feature.currency_exchange.domain.util.Utils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val currencyDao: CurrencyDao,
    private val currencyRateDao: CurrencyRateDao,
    private val api: WebApi,
    private val datastore: DataStorePreferences,
    private val context: Context
) : CurrencyRepository {
    override suspend fun fetchCurrencies(): Flow<Resource<List<Currency>>> {
        return flow {
            try {
                emit(Resource.Loading())
                val currencyRecords = currencyDao.getCurrencies()
                val currencies = currencyRecords.map { it.toCurrency() }

                if (currencies.isNotEmpty()) {
                    return@flow emit(Resource.Success(currencies))
                }

                val response = api.fetchCurrencies()
                response.body()?.let { currencyMap ->
                    currencyDao.removeCurrencies(currencyRecords)
                    currencyDao.insertCurrencies(currencyMap.map { (key, value) ->
                        CurrencyEntity(
                            key,
                            value
                        )
                    })
                    return@flow emit(
                        Resource.Success(
                            currencyDao.getCurrencies().map { it.toCurrency() })
                    )
                }
                emit(Resource.Error(message = context.getString(R.string.unexpected_error)))
            } catch (error: HttpException) {
                emit(
                    Resource.Error(
                        message = error.localizedMessage
                            ?: context.getString(R.string.something_went_wrong)
                    )
                )
            } catch (error: IOException) {
                emit(
                    Resource.Error(context.getString(R.string.could_not_connect))
                )
            }
        }
    }

    override suspend fun fetchExchangeRates(lastExchangeRatesTimeStamp: Long): Flow<Resource<List<CurrencyRate>>> {
        return flow {
            try {
                emit(Resource.Loading())
                val prevExchangeRateRecords = currencyRateDao.getCurrencyRates()
                val prevExchangeRates = prevExchangeRateRecords.map { it.toCurrencyRate() }

                val isThresholdCrossed =
                    Utils.isExchangeRatesTimeStampThresholdCrossed(lastExchangeRatesTimeStamp)

                if (prevExchangeRates.isNotEmpty() && !isThresholdCrossed) {
                    return@flow emit(Resource.Success(prevExchangeRates))
                }

                val response = api.fetchExchangeRates()
                response.body()?.let { exchangeRateDto ->
                    currencyRateDao.removeCurrencyRates(prevExchangeRateRecords)
                    currencyRateDao.insertCurrencyRates(exchangeRateDto.rates.map { it.toCurrencyRateEntity() })
                    datastore.saveTimestamp(System.currentTimeMillis())
                    return@flow emit(
                        Resource.Success(
                            currencyRateDao.getCurrencyRates().map { it.toCurrencyRate() })
                    )
                }
                emit(Resource.Error(message = context.getString(R.string.unexpected_error)))
            } catch (error: HttpException) {
                emit(
                    Resource.Error(
                        message = error.localizedMessage
                            ?: context.getString(R.string.something_went_wrong)
                    )
                )
            } catch (error: IOException) {
                emit(
                    Resource.Error(context.getString(R.string.could_not_connect))
                )
            }
        }
    }
}