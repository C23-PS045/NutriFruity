package com.linggash.nutrifruity.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linggash.nutrifruity.data.FruitRepository
import com.linggash.nutrifruity.model.FruitDetail
import com.linggash.nutrifruity.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val fruitRepository: FruitRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<UiState<FruitDetail>> =
        MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<FruitDetail>>
        get() = _uiState

    fun getFruitById(fruitId: Long){
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(fruitRepository.getFruitDetail(fruitId))
        }
    }
}