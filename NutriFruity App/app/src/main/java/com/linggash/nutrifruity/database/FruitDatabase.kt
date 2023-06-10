package com.linggash.nutrifruity.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        Fruit::class,
        Nutrition::class,
        Benefit::class,
        FruitNutritionCrossRef::class,
        FruitBenefitCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
abstract class FruitDatabase: RoomDatabase() {

    abstract fun fruitDao(): FruitDao
    companion object {
        @Volatile
        private var instance: FruitDatabase? = null
        fun getDatabase(context: Context): FruitDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    FruitDatabase::class.java, "News.db"
                ).build()
            }
    }
}