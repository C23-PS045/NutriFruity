package com.linggash.nutrifruity.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        FruitDataItem::class,
        NutritionData::class,
        BenefitData::class,
        FruitNutritionCrossRef::class,
        FruitBenefitCrossRef::class
    ],
    version = 1,
    exportSchema = false
)

abstract class FruitDatabase: RoomDatabase() {

    abstract fun fruitDao(): FruitDao

    companion object{
        @Volatile
        private var INSTANCE: FruitDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FruitDatabase {
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    FruitDatabase::class.java, FRUIT_DATABASE
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
        private const val FRUIT_DATABASE = "fruit_database"
    }
}