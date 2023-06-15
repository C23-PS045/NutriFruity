package com.linggash.nutrifruity.ui.result

import androidx.lifecycle.ViewModel
import com.linggash.nutrifruity.data.FruitRepository

class CameraResultViewModel (
    private val repository: FruitRepository
): ViewModel() {
    fun getFruitDetail(id: Long) = repository.getFruitDetail(id)
}