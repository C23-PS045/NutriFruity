package com.linggash.nutrifruity.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.linggash.nutrifruity.ui.component.TitleBanner

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
){
    Scaffold(
        topBar = { TopBar(modifier) }
    ) { innerPadding ->
        Box(modifier = modifier.padding(innerPadding))
    }
}

@Composable
fun TopBar(
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .padding(25.dp)
    ){
        TitleBanner("NUTRIFRUITY")
    }
}

@Composable
@Preview(
    showBackground = true
)
private fun HomePreview(){
    HomeScreen()
}