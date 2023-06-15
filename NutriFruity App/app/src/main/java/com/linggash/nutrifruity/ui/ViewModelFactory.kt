package com.linggash.nutrifruity.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.linggash.nutrifruity.data.FruitRepository
import com.linggash.nutrifruity.ui.detail.FruitDetailViewModel
import com.linggash.nutrifruity.ui.game.GameViewModel
import com.linggash.nutrifruity.ui.list.FruitListViewModel
import com.linggash.nutrifruity.ui.result.CameraResultViewModel
import com.linggash.nutrifruity.util.Injection

class ViewModelFactory private constructor(
    private val fruitRepository: FruitRepository,
    ) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FruitListViewModel::class.java)) {
            return FruitListViewModel(fruitRepository) as T
        } else if (modelClass.isAssignableFrom(FruitDetailViewModel::class.java)){
            return FruitDetailViewModel(fruitRepository) as T
        } else  if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(fruitRepository) as T
        } else if (modelClass.isAssignableFrom(CameraResultViewModel::class.java)) {
            return CameraResultViewModel(fruitRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideRepository(context),
                )
            }.also { instance = it }
    }
}