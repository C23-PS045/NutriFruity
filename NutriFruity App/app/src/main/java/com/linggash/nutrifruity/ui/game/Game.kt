package com.linggash.nutrifruity.ui.game

import com.linggash.nutrifruity.database.Fruit
import com.linggash.nutrifruity.model.FruitChoice

object Game {

    fun getChoice(list: List<Fruit>): List<FruitChoice>{
        val shuffledFruit = list.shuffled().take(5)
        val listChoice = shuffledFruit.map { fruit->
            var choices : MutableList<String> = shuffledFruit
                .filterNot { it.name == fruit.name }
                .take(2)
                .map { it.name } as MutableList<String>
            choices.add(fruit.name)
            choices = choices.shuffled() as MutableList<String>
            FruitChoice(
                image = fruit.photoUrl,
                answer = fruit.name,
                listChoice = choices
            )
        }
        return listChoice
    }
}