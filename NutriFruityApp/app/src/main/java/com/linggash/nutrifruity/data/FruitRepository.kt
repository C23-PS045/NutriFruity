package com.linggash.nutrifruity.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.linggash.nutrifruity.database.FruitBenefitCrossRef
import com.linggash.nutrifruity.database.FruitDataItem
import com.linggash.nutrifruity.database.FruitDatabase
import com.linggash.nutrifruity.database.FruitNutritionCrossRef
import com.linggash.nutrifruity.database.FruitWithNutritionAndBenefit
import com.linggash.nutrifruity.model.FruitDetail
import com.linggash.nutrifruity.network.ApiService

class FruitRepository(private val fruitDatabase: FruitDatabase, private val apiService: ApiService) {

    fun getFruit(): Pager<Int, FruitDataItem> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5,
            ),
            remoteMediator = FruitRemoteMediator(fruitDatabase, apiService),
            pagingSourceFactory = {
                fruitDatabase.fruitDao().getAllFruitAsPagingSource()
            }
        )
    }

    suspend fun getFruitDetail(id: Long): FruitDetail {
        return apiService.getFruitDetail(id)
//        val fruitId = fruitDatabase.fruitDao().getAllFruit().filter {
//            it.fruitId == id
//        }
//        if (fruitId == null){
//            val fruitDetail = apiService.getFruitDetail(id)
//            fruitDatabase.fruitDao().insertFruitNutritionCrossRef(fruitDetail.nutrition.map {
//                FruitNutritionCrossRef(id, it.id)
//            })
//            fruitDatabase.fruitDao().insertFruitBenefitCrossRef(fruitDetail.benefit.map {
//                FruitBenefitCrossRef(id, it.id)
//            })
//            return fruitDatabase.fruitDao().getAllFruitWithNutritionAndBenefit().first {
//                it.fruit.fruitId == id
//            }
//        }else{
//            return fruitDatabase.fruitDao().getAllFruitWithNutritionAndBenefit().first {
//                it.fruit.fruitId == id
//            }
//        }
    }
}