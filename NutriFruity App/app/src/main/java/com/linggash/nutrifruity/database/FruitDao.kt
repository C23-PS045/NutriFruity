package com.linggash.nutrifruity.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface FruitDao {
    @Query("SELECT * FROM fruit")
    fun getAllFruit(): LiveData<List<Fruit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFruit(fruit: List<Fruit>)

    @Query("DELETE FROM fruit")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNutrition(nutrition: List<Nutrition>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFruitNutritionCrossRef(fruitNutritionCrossRef: List<FruitNutritionCrossRef>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBenefit(benefit: List<Benefit>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFruitBenefitCrossRef(fruitBenefitCrossRef: List<FruitBenefitCrossRef>)

    @Transaction
    @Query("SELECT * from fruit")
    fun getAllFruitWithNutritionAndBenefit():  List<FruitWithNutritionAndBenefit>
}