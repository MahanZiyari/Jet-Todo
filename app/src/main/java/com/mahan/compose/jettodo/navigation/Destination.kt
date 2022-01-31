package com.mahan.compose.jettodo.navigation

import java.lang.IllegalArgumentException

enum class Destination {
    ListScreen,
    TaskScreen;

    companion object {
        fun fromRoute(route: String?): Destination {
            return when (route?.substringBefore("/")) {
                ListScreen.name -> ListScreen
                TaskScreen.name -> TaskScreen
                null -> ListScreen
                else -> throw IllegalArgumentException("yooho whats up?")
            }
        }
    }
}