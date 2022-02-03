package com.mahan.compose.jettodo.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mahan.compose.jettodo.ui.screens.ListScreen
import com.mahan.compose.jettodo.ui.screens.TaskScreen
import com.mahan.compose.jettodo.ui.viewmodels.SharedViewModel
import com.mahan.compose.jettodo.util.Action

@ExperimentalMaterialApi
@Composable
fun SetupNavigation(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {

    val navigateToListScreen: (Action) -> Unit = {
        navController.navigate(route = Destination.ListScreen.name + "/${it.name}") {
            popUpTo(Destination.ListScreen.name + "/${it.name}") {inclusive = true}
        }
    }

    val navigateToTaskScreen: (Int) ->Unit = {
        navController.navigate(route = Destination.TaskScreen.name + "/$it")
    }

    NavHost(
        navController = navController,
        startDestination = Destination.ListScreen.name + "/{action}"
    ) {
        // ListScreen
        composable(
            route = Destination.ListScreen.name + "/{action}",
            arguments = listOf(navArgument(name = "action") { type = NavType.StringType })
        ) {
            val action = it.arguments?.getString("action")
            ListScreen(
                action = action,
                navigateToTaskScreen = navigateToTaskScreen,
                sharedViewModel = sharedViewModel
            )
        }

        // TaskScreen
        composable(
            route = Destination.TaskScreen.name + "/{taskId}",
            arguments = listOf(navArgument(name = "taskId") { type = NavType.IntType })
        ) {
            val taskId = it.arguments!!.getInt("taskId")
            sharedViewModel.getSelectedTask(taskId = taskId)

            // Act as an state for Task Screen
            val selectedTask by sharedViewModel.selectedTask.collectAsState()

            TaskScreen(
                selectedTask = selectedTask,
                navigateToListScreen = navigateToListScreen
            )
        }
    }
}