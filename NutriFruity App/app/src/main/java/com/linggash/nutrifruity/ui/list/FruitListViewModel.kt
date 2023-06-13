package com.linggash.nutrifruity.ui.list

import androidx.lifecycle.ViewModel
import com.linggash.nutrifruity.data.FruitRepository

class FruitListViewModel  constructor(
    private val repository: FruitRepository
): ViewModel(){
    fun getFruit() = repository.getFruit()
}

