package com.mahan.compose.jettodo.navigation

enum class Destination {
    ListScreen,
    TaskScreen,
    SplashScreen;

    companion object {
        fun fromRoute(route: String?): Destination {
            return when (route?.substringBefore("/")) {
                ListScreen.name -> ListScreen
                TaskScreen.name -> TaskScreen
                SplashScreen.name -> SplashScreen
                null -> ListScreen
                else -> throw IllegalArgumentException("Route Not Found")
            }
        }
    }
}