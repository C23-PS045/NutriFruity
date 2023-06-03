package com.linggash.nutrifruity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.linggash.nutrifruity.ui.NutriFruityApp
import com.linggash.nutrifruity.ui.screen.list.FruitListViewModel
import com.linggash.nutrifruity.ui.theme.NutriFruityTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                val viewModel = hiltViewModel<FruitListViewModel>()
                NutriFruityTheme() {
                    NutriFruityApp(
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}