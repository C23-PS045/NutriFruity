package com.linggash.nutrifruity.di

import android.content.Context
import androidx.paging.Pager
import com.linggash.nutrifruity.data.FruitRepository
import com.linggash.nutrifruity.database.FruitDataItem
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
    fun provide(): ApiService{
        return ApiConfig.getApiService()
    }

    @Provides
    @Singleton
    fun provideFruitPager(fruitDb: FruitDatabase, apiService: ApiService): Pager<Int, FruitDataItem> {
        return FruitRepository(fruitDb, apiService).getFruit()
    }
}