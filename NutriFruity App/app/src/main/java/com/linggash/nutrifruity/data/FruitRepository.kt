package com.linggash.nutrifruity.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.linggash.nutrifruity.database.Fruit
import com.linggash.nutrifruity.database.FruitBenefitCrossRef
import com.linggash.nutrifruity.database.FruitDao
import com.linggash.nutrifruity.database.FruitNutritionCrossRef
import com.linggash.nutrifruity.database.FruitWithNutritionAndBenefit
import com.linggash.nutrifruity.network.ApiService
import com.linggash.nutrifruity.util.toBenefitData
import com.linggash.nutrifruity.util.toFruitData
import com.linggash.nutrifruity.util.toNutritionData

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
            Result.Success(it)
        }
        emitSource(localData)
    }

    fun getFruitDetail(id: Long): LiveData<Result<FruitWithNutritionAndBenefit>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getFruitDetail(id)
            fruitDao.insertNutrition(response.nutritionResponse.map { it.toNutritionData() })
            fruitDao.insertBenefit(response.benefitResponse.map { it.toBenefitData() })
            fruitDao.insertFruitNutritionCrossRef(response.nutritionResponse.map { FruitNutritionCrossRef(fId = response.id, nId = it.id) })
            fruitDao.insertFruitBenefitCrossRef(response.benefitResponse.map { FruitBenefitCrossRef(response.id, it.id) })
        } catch (e: Exception) {
            Log.d("FruitRespository", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<FruitWithNutritionAndBenefit>> = fruitDao.getAllFruitWithNutritionAndBenefit(id).map {
            Result.Success(it)
        }
        emitSource(localData)
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