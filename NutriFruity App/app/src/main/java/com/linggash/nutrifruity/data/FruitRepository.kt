package com.linggash.nutrifruity.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.linggash.nutrifruity.database.Fruit
import com.linggash.nutrifruity.database.FruitDao
import com.linggash.nutrifruity.database.FruitDatabase
import com.linggash.nutrifruity.model.FruitDetailResponse
import com.linggash.nutrifruity.network.ApiService
import com.linggash.nutrifruity.util.toFruitData

class FruitRepository(
    private val fruitDao: FruitDao,
    private val apiService: ApiService,
) {
    fun getFruit(): LiveData<Result<List<Fruit>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getFruit()
            val fruitList = response.map { it.toFruitData() }
            fruitDao.deleteAll()
            fruitDao.insertFruit(fruitList)
        } catch (e: Exception) {
            Log.d("FruitRepository", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<Fruit>>> = fruitDao.getAllFruit().map {
            Log.d("hasi", it.toString())
            Result.Success(it)
        }
        emitSource(localData)
    }

    suspend fun getFruitDetail(id: Long): FruitDetailResponse {
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

    companion object {
        @Volatile
        private var instance: FruitRepository? = null
        fun getInstance(
            fruitDao: FruitDao,
            apiService: ApiService
        ): FruitRepository =
            instance ?: synchronized(this) {
                instance ?: FruitRepository(fruitDao, apiService)
            }.also { instance = it }
    }

}