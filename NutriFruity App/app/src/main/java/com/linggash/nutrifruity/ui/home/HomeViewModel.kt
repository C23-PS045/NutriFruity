package com.linggash.nutrifruity.ui.home

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.linggash.nutrifruity.data.SettingPreferences

class HomeViewModel(
    dataStore: DataStore<Preferences>
) : ViewModel() {

    private val pref = SettingPreferences.getInstance(dataStore)

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getSoundSetting().asLiveData()
    }
}