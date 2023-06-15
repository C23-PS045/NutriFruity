package com.linggash.nutrifruity.ui.list

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.linggash.nutrifruity.data.FruitRepository
import com.linggash.nutrifruity.data.SettingPreferences
import kotlinx.coroutines.launch

class FruitListViewModel  constructor(
    private val repository: FruitRepository,
    dataStore: DataStore<Preferences>
): ViewModel(){
    fun getFruit() = repository.getFruit()
    private val pref = SettingPreferences.getInstance(dataStore)
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getSoundSetting().asLiveData()
    }
}

