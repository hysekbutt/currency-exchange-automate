package com.tahadardev.exchangerate.feature.currency_exchange.domain.util

import com.tahadardev.exchangerate.common.Constants

object Utils {

    fun getConversionRate(searchValue: String, exchangeRate: Double): String {

        val userSearchValue : Double = searchValue.toDouble()
        val convertedRate :Double = userSearchValue * exchangeRate

//        return convertedRate.toString()
        return "%.2f".format(convertedRate)
    }

    fun isExchangeRatesTimeStampThresholdCrossed(lastExchangeRatesTimeStamp : Long) : Boolean {
        val currTimeStamp: Long = System.currentTimeMillis()
        val timeDiff = currTimeStamp - lastExchangeRatesTimeStamp

        return timeDiff > Constants.EXCHANGE_RATE_UPDATE_THRESHOLD
    }
 }