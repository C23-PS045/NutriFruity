package com.linggash.nutrifruity.database

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface FruitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFruit(fruit: List<FruitDataItem>)

    @Query("SELECT * FROM fruit")
    fun getAllFruit(): PagingSource<Int, FruitDataItem>

    @Query("DELETE FROM fruit")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNutrition(nutrition: List<NutritionData>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFruitNutritionCrossRef(fruitNutritionCrossRef: List<FruitNutritionCrossRef>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBenefit(benefit: List<BenefitData>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFruitBenefitCrossRef(fruitBenefitCrossRef: List<FruitBenefitCrossRef>)

    @Transaction
    @Query("SELECT * from fruit")
    fun getAllFruitWithNutritionAndBenefit(): PagingSource<Int, FruitWithNutritionAndBenefit>
}