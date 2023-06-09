package com.linggash.nutrifruity.di

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingData
import com.linggash.nutrifruity.data.FruitRepository
import com.linggash.nutrifruity.database.Fruit
import com.linggash.nutrifruity.database.FruitDatabase
import com.linggash.nutrifruity.network.ApiConfig
import com.linggash.nutrifruity.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFruitDatabase(@ApplicationContext context: Context): FruitDatabase {
        return FruitDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideApiService(): ApiService{
        return ApiConfig.getApiService()
    }

    @Provides
    @Singleton
    fun provideRepository(fruitDb: FruitDatabase, apiService: ApiService) = FruitRepository(fruitDb, apiService)
}