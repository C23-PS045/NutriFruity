package com.linggash.nutrifruity.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val SOUND_KEY = booleanPreferencesKey("sound_setting")

    fun getSoundSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[SOUND_KEY] ?: false
        }
    }

    suspend fun saveSoundSetting(isSoundOn: Boolean) {
        dataStore.edit { preferences ->
            preferences[SOUND_KEY] = isSoundOn
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}