package com.tahadardev.exchangerate.feature.currency_exchange.domain.util

import com.tahadardev.exchangerate.common.Constants
import org.junit.Assert.*
import org.junit.Test

class UtilsTest {

    @Test
    fun convertedRateShouldBeMultipleOfUserValueAndExchangeRate() {
        val convertedRate = Utils.getConversionRate("2", 5.00)

        assertTrue(convertedRate == "10.00")
    }

    @Test
    fun convertedRateShouldBeFormattedToTwoDecimalPlaces() {
        val convertedRate = Utils.getConversionRate("3", 0.333)

        assertTrue(convertedRate == "1.00")
    }

    @Test
    fun largeSearchValueShouldGiveFormattedConvertedRateToTwoDecimalPlaces () {
        val convertedRate = Utils.getConversionRate("11231232.12", 1.8222)

        assertTrue(convertedRate == "20465551.17")
    }

    @Test
    fun timeDifferenceSmallerThan30MinShouldReturnFalse() {
        val savedTime = System.currentTimeMillis()
        val isThresholdCrossed = Utils.isExchangeRatesTimeStampThresholdCrossed(savedTime)

        assertFalse(isThresholdCrossed)
    }

    @Test
    fun timeDifferenceGreaterThan30MinShouldReturnTrue() {
        val savedTime = System.currentTimeMillis() - Constants.EXCHANGE_RATE_UPDATE_THRESHOLD - 1000
        val isThresholdCrossed = Utils.isExchangeRatesTimeStampThresholdCrossed(savedTime)

        assertTrue(isThresholdCrossed)
    }
}