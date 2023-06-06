package com.linggash.nutrifruity.ui.screen.list

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.linggash.nutrifruity.ui.component.FruitItem
import com.linggash.nutrifruity.ui.theme.GreenPrimary
import com.linggash.nutrifruity.ui.theme.GreenSecondary

@Composable
fun FruitListScreen(
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
    viewModel: FruitListViewModel
){
    val fruit = viewModel.fruitPagingFlow.collectAsLazyPagingItems()
    val context = LocalContext.current
    LaunchedEffect(key1 = fruit.loadState) {
        if (fruit.loadState.refresh is LoadState.Error) {
            Toast.makeText(context, "Error ${(fruit.loadState.refresh as LoadState.Error).error.message}", Toast.LENGTH_SHORT)
                .show()
            Log.e("listbuah", (fruit.loadState.refresh as LoadState.Error).error.message.toString())
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ){
        if (fruit.loadState.refresh is LoadState.Loading){
            CircularProgressIndicator(
                modifier = modifier.align(Alignment.Center)
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(160.dp),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement =  Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = modifier
            ){
                items(
                    count = fruit.itemCount,
                    key = fruit.itemKey {it.fruitId},
                ){ index ->
                    val item = fruit[index]
                    if (item != null) {
                        FruitItem(
                            borderColor = GreenSecondary,
                            cardColor = GreenPrimary,
                            name = item.name,
                            image = item.photoUrl,
                            onClick = {navigateToDetail(item.fruitId)},
                            modifier = modifier.fillMaxHeight()
                        )
                    }
                }
            }
        }
    }
}