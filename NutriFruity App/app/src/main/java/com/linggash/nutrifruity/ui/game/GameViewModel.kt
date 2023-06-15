package com.linggash.nutrifruity.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.linggash.nutrifruity.data.FruitRepository

class GameViewModel(
    private val repository: FruitRepository
): ViewModel() {

    private val _number = MutableLiveData(0)
    val number : LiveData<Int> = _number

    fun getFruit() = repository.getFruit()

    fun next(){
        _number.value = _number.value?.plus(1)
    }
}