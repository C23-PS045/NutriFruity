package com.linggash.nutrifruity.ui

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.linggash.nutrifruity.data.FruitRepository
import com.linggash.nutrifruity.ui.detail.FruitDetailViewModel
import com.linggash.nutrifruity.ui.game.GameViewModel
import com.linggash.nutrifruity.ui.home.HomeViewModel
import com.linggash.nutrifruity.ui.list.FruitListViewModel
import com.linggash.nutrifruity.ui.setting.SettingViewModel
import com.linggash.nutrifruity.util.Injection

class ViewModelFactory private constructor(
    private val fruitRepository: FruitRepository,
    private val dataStore: DataStore<Preferences>
    ) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FruitListViewModel::class.java)) {
            return FruitListViewModel(fruitRepository,dataStore) as T
        } else if (modelClass.isAssignableFrom(FruitDetailViewModel::class.java)){
            return FruitDetailViewModel(fruitRepository) as T
        } else  if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(fruitRepository) as T
        } else if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(dataStore) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(dataStore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context, dataStore: DataStore<Preferences>): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideRepository(context),
                    dataStore
                )
            }.also { instance = it }
    }
}