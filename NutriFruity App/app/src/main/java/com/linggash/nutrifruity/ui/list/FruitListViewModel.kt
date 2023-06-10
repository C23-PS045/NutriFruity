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

class ViewModelFactory private constructor(private val newsRepository: FruitRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FruitListViewModel::class.java)) {
            return FruitListViewModel(newsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}