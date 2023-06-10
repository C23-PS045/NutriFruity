package com.linggash.nutrifruity.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "fruit")
data class Fruit(

    @PrimaryKey
    val fruitId: Long,

    val name: String,

    val photoUrl: String,
): Parcelable

@Entity(tableName = "nutrition")
data class Nutrition(

    @PrimaryKey
    val nutriId: Long,

    val nutrition: String,
)

@Entity(tableName = "benefit")
data class Benefit(

    @PrimaryKey
    val benId: Long,

    val benefit: String,
)

@Entity(primaryKeys = ["fId", "nId"])
data class FruitNutritionCrossRef(
    val fId: Long,
    @ColumnInfo(index = true)
    val nId: Long,
)

@Entity(primaryKeys = ["fId", "bId"])
data class FruitBenefitCrossRef(
    val fId: Long,
    @ColumnInfo(index = true)
    val bId: Long,
)

data class FruitWithNutritionAndBenefit(
    @Embedded
    val fruit: Fruit,

    @Relation(
        parentColumn = "fruitId",
        entity = Nutrition::class,
        entityColumn = "nutriId",
        associateBy = Junction(
            value = FruitNutritionCrossRef::class,
            parentColumn = "fId",
            entityColumn = "nId"
        )
    )
    val nutrition: List<Nutrition>,

    @Relation(
        parentColumn = "fruitId",
        entity = Benefit::class,
        entityColumn = "benId",
        associateBy = Junction(
            value = FruitBenefitCrossRef::class,
            parentColumn = "fId",
            entityColumn = "bId"
        )
    )
    val benefit: List<Benefit>
)