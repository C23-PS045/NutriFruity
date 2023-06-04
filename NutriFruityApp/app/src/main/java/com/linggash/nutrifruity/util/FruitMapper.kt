package com.linggash.nutrifruity.util

import com.linggash.nutrifruity.database.BenefitData
import com.linggash.nutrifruity.database.FruitDataItem
import com.linggash.nutrifruity.database.NutritionData
import com.linggash.nutrifruity.model.Fruit

fun Fruit.toFruitData(): FruitDataItem {
    return FruitDataItem(
        fruitId = id,
        name = name.uppercase(),
        photoUrl = photoUrl,
    )
}