package com.tahadardev.exchangerate.di

import android.content.Context
import com.tahadardev.exchangerate.common.DataStorePreferences
import com.tahadardev.exchangerate.feature.currency_exchange.data.local.CurrencyExchangeDatabase
import com.tahadardev.exchangerate.feature.currency_exchange.data.remote.WebApi
import com.tahadardev.exchangerate.feature.currency_exchange.data.repository.CurrencyRepositoryImpl
import com.tahadardev.exchangerate.feature.currency_exchange.domain.repository.CurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideCurrencyRepo(
        db: CurrencyExchangeDatabase,
        dataStorePreferences: DataStorePreferences,
        api: WebApi,
        @ApplicationContext context : Context
    ): CurrencyRepository {
        return CurrencyRepositoryImpl(
            db.currencyDao,
            db.currencyRateDao,
            api,
            dataStorePreferences,
            context,
        )
    }
}
