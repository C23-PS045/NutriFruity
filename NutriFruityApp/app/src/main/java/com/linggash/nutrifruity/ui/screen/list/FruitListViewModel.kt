package com.linggash.nutrifruity.ui.screen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.linggash.nutrifruity.database.FruitDataItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class FruitListViewModel @Inject constructor(
    pager: Pager<Int, FruitDataItem>
): ViewModel() {

    val fruitPagingFlow = pager
        .flow
        .map { pagingData ->
            pagingData
        }
        .cachedIn(viewModelScope)
}