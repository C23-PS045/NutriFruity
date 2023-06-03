package com.linggash.nutrifruity.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.linggash.nutrifruity.database.FruitDataItem
import com.linggash.nutrifruity.database.FruitDatabase
import com.linggash.nutrifruity.network.ApiService
import com.linggash.nutrifruity.util.toFruitData

@OptIn(ExperimentalPagingApi::class)
class FruitRemoteMediator(
    private val database: FruitDatabase,
    private val apiService: ApiService
) : RemoteMediator<Int, FruitDataItem>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FruitDataItem>
    ): MediatorResult {
        try {
            val responseData = apiService.getFruit()
            val listFruit = responseData.map {
                it.toFruitData()
            }
            val endOfPaginationReached = true
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.fruitDao().deleteAll()
                }
                database.fruitDao().insertFruit(listFruit)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }
}