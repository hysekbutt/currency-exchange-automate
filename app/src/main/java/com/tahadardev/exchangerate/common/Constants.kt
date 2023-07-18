package com.tahadardev.exchangerate.common

object Constants {
    const val BASE_URL = "https://openexchangerates.org/api/"
    const val DB_NAME = "currency_rate_db"
    const val PREF_DATASTORE_NAME = "currency_preference_datastore"
    const val EXCHANGE_RATE_UPDATE_THRESHOLD = 1800000  // Milliseconds in 30 minutes
}