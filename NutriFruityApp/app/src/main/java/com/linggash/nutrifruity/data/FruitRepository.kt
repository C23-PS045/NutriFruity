package com.linggash.nutrifruity.data

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.linggash.nutrifruity.database.FruitDataItem
import com.linggash.nutrifruity.database.FruitDatabase
import com.linggash.nutrifruity.network.ApiService

class FruitRepository(private val fruitDatabase: FruitDatabase, private val apiService: ApiService) {

    fun getFruit(): LiveData<PagingData<FruitDataItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5,
            ),
            remoteMediator = FruitRemoteMediator(fruitDatabase, apiService),
            pagingSourceFactory = {
                fruitDatabase.fruitDao().getAllFruit()
            }
        ).liveData
    }
}