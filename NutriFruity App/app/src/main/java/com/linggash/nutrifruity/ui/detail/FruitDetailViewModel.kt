package com.linggash.nutrifruity.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.linggash.nutrifruity.data.FruitRepository
import com.linggash.nutrifruity.model.FruitDetailResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class FruitDetailViewModel @Inject constructor(
    private val repository: FruitRepository
): ViewModel() {
    private var _fruitDetail = MutableLiveData<FruitDetailResponse>()
    val fruitDetail : LiveData<FruitDetailResponse> = _fruitDetail

    fun getFruitDetail(id: Long){
        runBlocking {
            val response = repository.getFruitDetail(id)
            _fruitDetail.value = response
        }
    }
}