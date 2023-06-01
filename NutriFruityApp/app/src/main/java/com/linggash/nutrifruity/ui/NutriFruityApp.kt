package com.linggash.nutrifruity.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.linggash.nutrifruity.R
import com.linggash.nutrifruity.ui.navigation.NavigationItem
import com.linggash.nutrifruity.ui.navigation.Screen
import com.linggash.nutrifruity.ui.screen.home.HomeScreen
import com.linggash.nutrifruity.ui.screen.list.FruitListScreen
import com.linggash.nutrifruity.ui.screen.setting.SettingScreen
import com.linggash.nutrifruity.ui.screen.splash.SplashScreen
import com.linggash.nutrifruity.ui.theme.OrangePrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutriFruityApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            if (currentRoute == Screen.Home.route || currentRoute == Screen.Setting.route){
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = modifier.padding(innerPadding),
        ){
            composable(Screen.Splash.route){
                SplashScreen(navController = navController)
            }
            composable(Screen.Home.route) {
                HomeScreen(navController = navController)
            }
            composable(Screen.Setting.route) {
                SettingScreen()
            }
            composable(Screen.FruitList.route) {
                FruitListScreen(navController = navController)
            }
        }
    }
}

@Composable
private fun BottomBar(
    navController : NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar (
        modifier = modifier,
    ) {
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_setting),
                icon = Icons.Default.Settings,
                screen = Screen.Setting
            )
        )
        NavigationBar(
            containerColor = Color.White
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute  = navBackStackEntry?.destination?.route
            navigationItems.map { items ->
                NavigationBarItem(
                    icon ={
                        Icon(
                            imageVector = items.icon,
                            contentDescription = items.title
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = OrangePrimary,
                        indicatorColor = Color.White
                    ),
                    selected = currentRoute == items.screen.route,
                    onClick = {
                        navController.navigate(items.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}