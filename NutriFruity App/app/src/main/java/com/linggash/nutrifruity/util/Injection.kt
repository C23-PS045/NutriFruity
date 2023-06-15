package com.linggash.nutrifruity.util

import android.content.Context
import com.linggash.nutrifruity.data.FruitRepository
import com.linggash.nutrifruity.database.FruitDatabase
import com.linggash.nutrifruity.network.ApiConfig

object Injection {
    fun provideRepository(context: Context): FruitRepository {
        val apiService = ApiConfig.getApiService()
        val database = FruitDatabase.getDatabase(context)
        val dao = database.fruitDao()
        return FruitRepository.getInstance(dao, apiService)
    }
}