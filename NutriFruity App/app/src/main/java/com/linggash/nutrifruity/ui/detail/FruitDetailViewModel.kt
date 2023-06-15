package com.linggash.nutrifruity.ui.detail

import androidx.lifecycle.ViewModel
import com.linggash.nutrifruity.data.FruitRepository

class FruitDetailViewModel (
    private val repository: FruitRepository
): ViewModel() {
    fun getFruitDetail(id: Long) = repository.getFruitDetail(id)
}