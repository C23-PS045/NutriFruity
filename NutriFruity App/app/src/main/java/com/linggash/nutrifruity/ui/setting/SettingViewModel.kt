package com.linggash.nutrifruity.ui.setting

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.linggash.nutrifruity.data.SettingPreferences
import kotlinx.coroutines.launch

class SettingViewModel(
    val dataStore: DataStore<Preferences>
) : ViewModel() {
    val pref = SettingPreferences.getInstance(dataStore)
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getSoundSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveSoundSetting(isDarkModeActive)
        }
    }
}