package com.linggash.nutrifruity.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.linggash.nutrifruity.data.FruitRepository
import com.linggash.nutrifruity.database.Fruit
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FruitListViewModel @Inject constructor(
    private val repository: FruitRepository
): ViewModel(){

    fun fruit() :LiveData<PagingData<Fruit>> = repository.getFruit()
}