package com.linggash.nutrifruity.ui.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.linggash.nutrifruity.R
import com.linggash.nutrifruity.ui.navigation.Screen
import com.linggash.nutrifruity.ui.theme.orangePrimary
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navController : NavHostController,
){
    Scaffold { innerPadding ->
       Splash(modifier.padding(innerPadding))
    }
    LaunchedEffect(key1 = true){
        delay(3000)
        navController.popBackStack()
        navController.navigate(Screen.Home.route)
    }
}

@Composable
fun Splash(
    modifier: Modifier
){
    Box(
        modifier = modifier
            .background(orangePrimary)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(R.drawable.splash_logo),
            contentDescription = stringResource(R.string.app_name),
        )
    }
}