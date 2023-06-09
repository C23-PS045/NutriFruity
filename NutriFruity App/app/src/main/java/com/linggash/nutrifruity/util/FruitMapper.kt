package com.linggash.nutrifruity.util

import com.linggash.nutrifruity.database.Fruit
import com.linggash.nutrifruity.model.FruitResponse

fun FruitResponse.toFruitData(): Fruit {
    return Fruit(
        fruitId = id,
        name = name.uppercase(),
        photoUrl = photoUrl,
    )
}