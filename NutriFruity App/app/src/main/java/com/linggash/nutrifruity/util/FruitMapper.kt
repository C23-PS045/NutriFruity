package com.linggash.nutrifruity.util

import com.linggash.nutrifruity.database.Benefit
import com.linggash.nutrifruity.database.Fruit
import com.linggash.nutrifruity.database.Nutrition
import com.linggash.nutrifruity.model.BenefitResponse
import com.linggash.nutrifruity.model.FruitResponse
import com.linggash.nutrifruity.model.NutritionResponse

fun FruitResponse.toFruitData(): Fruit {
    return Fruit(
        fruitId = id,
        name = name.uppercase(),
        photoUrl = photoUrl,
        color = color,
        borderColor = borderColor
    )
}

fun NutritionResponse.toNutritionData(): Nutrition {
    return Nutrition(
        nutriId = id,
        nutrition = nutrition
    )
}

fun BenefitResponse.toBenefitData(): Benefit {
    return Benefit(
        benId = id,
        benefit = benefit
    )
}