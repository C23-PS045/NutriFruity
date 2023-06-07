package com.linggash.nutrifruity.ui.navigation

import java.io.File

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Setting : Screen("setting")
    object Splash : Screen("splash")
    object FruitList : Screen("list")
    object FruitDetail : Screen("list/{fruitId}") {
        fun createRoute(fruitId: Long) = "list/$fruitId"
    }
    object Camera : Screen("camera")
    object CameraResult : Screen("camera/{file}") {
        fun createRoute(file: File) = "camera/$file"
    }
}
