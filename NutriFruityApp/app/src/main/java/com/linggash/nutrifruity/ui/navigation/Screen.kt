package com.linggash.nutrifruity.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Setting : Screen("setting")
    object Splash : Screen("splash")
    object FruitList : Screen("list")
    object FruitDetail : Screen("list/{fruitId}"){
        fun createRoute(fruitId: Long) = "list/$fruitId"
    }
}
