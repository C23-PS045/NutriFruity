package com.linggash.nutrifruity.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation


@Entity(tableName = "fruit")
data class FruitDataItem(

    @PrimaryKey
    val fruitId: Long,

    val name: String,

    val photoUrl: String,
)

@Entity(tableName = "nutrition")
data class NutritionData(

    @PrimaryKey
    val nutriId: Long,

    val nutrition: Long,
)

@Entity(tableName = "benefit")
data class BenefitData(

    @PrimaryKey
    val benId: Long,

    val nutrition: Long,
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
    val fruit: FruitDataItem,

    @Relation(
        parentColumn = "fruitId",
        entity = NutritionData::class,
        entityColumn = "nutriId",
        associateBy = Junction(
            value = FruitNutritionCrossRef::class,
            parentColumn = "fId",
            entityColumn = "nId"
        )
    )
    val nutrition: List<NutritionData>,

    @Relation(
        parentColumn = "fruitId",
        entity = BenefitData::class,
        entityColumn = "benId",
        associateBy = Junction(
            value = FruitBenefitCrossRef::class,
            parentColumn = "fId",
            entityColumn = "bId"
        )
    )
    val benefit: List<BenefitData>
)