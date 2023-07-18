package com.tahadardev.exchangerate.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.tahadardev.exchangerate.BuildConfig
import com.tahadardev.exchangerate.common.Constants
import com.tahadardev.exchangerate.common.DataStorePreferences
import com.tahadardev.exchangerate.feature.currency_exchange.data.local.CurrencyExchangeDatabase
import com.tahadardev.exchangerate.feature.currency_exchange.data.remote.WebApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    } else {
        OkHttpClient.Builder().build()
    }

    @Singleton
    @Provides
    fun provideWebApi(okHttpClient: OkHttpClient): WebApi = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(Constants.BASE_URL)
        .build()
        .create(WebApi::class.java)

    @Singleton
    @Provides
    fun provideDatabase(app: Application): CurrencyExchangeDatabase {
        return Room.databaseBuilder(
            app,
            CurrencyExchangeDatabase::class.java,
            Constants.DB_NAME)
            .build()
    }

    @Provides
    @Singleton
    fun provideDataStorePref(@ApplicationContext context: Context) = DataStorePreferences(context)

}