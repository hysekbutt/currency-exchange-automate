package com.tahadardev.exchangerate.common

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(
    name = Constants.PREF_DATASTORE_NAME
)

class DataStorePreferences @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore = context.dataStore

    private object PreferencesKeys {
        val TIMESTAMP = longPreferencesKey("timestamp")
    }

    val timestamp: Flow<Long> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.TIMESTAMP] ?: 0L
    }

    suspend fun saveTimestamp(timestamp: Long) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.TIMESTAMP] = timestamp
        }
    }
}