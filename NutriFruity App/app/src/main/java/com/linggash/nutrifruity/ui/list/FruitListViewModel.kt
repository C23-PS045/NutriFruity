package com.linggash.nutrifruity.ui.list

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.linggash.nutrifruity.data.FruitRepository
import com.linggash.nutrifruity.util.Injection

class FruitListViewModel  constructor(
    private val repository: FruitRepository
): ViewModel(){
    fun getFruit() = repository.getFruit()
}

