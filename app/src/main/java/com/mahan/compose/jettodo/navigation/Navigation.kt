package com.mahan.compose.jettodo.navigation


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mahan.compose.jettodo.ui.screens.ListScreen
import com.mahan.compose.jettodo.ui.screens.SplashScreen
import com.mahan.compose.jettodo.ui.screens.TaskScreen
import com.mahan.compose.jettodo.ui.viewmodels.SharedViewModel
import com.mahan.compose.jettodo.util.Action
import com.mahan.compose.jettodo.util.toPriority

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun SetupNavigation(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {

    val backToListScreen: (Action) -> Unit = {
        navController.popBackStack(route = Destination.ListScreen.name + "/{action}", inclusive = false)
        // navController.popBackStack()
    }

    val navigateToListScreen: (Action) -> Unit = {
        navController.navigate(route = Destination.ListScreen.name + "/${it.name}") {
            popUpTo(0)
        }
    }

    val navigateToTaskScreen: (Int) ->Unit = {
        navController.navigate(route = Destination.TaskScreen.name + "/$it")
    }

    NavHost(
        navController = navController,
        startDestination = Destination.SplashScreen.name
    ) {
        composable(route = Destination.SplashScreen.name) {
            SplashScreen {
                navigateToListScreen(Action.NO_ACTION)
            }
        }

        // ListScreen
        composable(
            route = Destination.ListScreen.name + "/{action}",
            arguments = listOf(navArgument(name = "action") { type = NavType.StringType })
        ) {

            ListScreen(
                // action = action,
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
            LaunchedEffect(key1 = taskId) {
                sharedViewModel.getSelectedTask(taskId = taskId)
            }

            // Act as an state for Task Screen
            val selectedTask by sharedViewModel.selectedTask.collectAsState()

            LaunchedEffect(key1 = selectedTask) {
                if (selectedTask != null || taskId == -1)
                    sharedViewModel.updateTaskContentFields(selectedTask = selectedTask)
            }

            TaskScreen(
                selectedTask = selectedTask,
                navigateToListScreen = navigateToListScreen,
                sharedViewModel = sharedViewModel
            )
        }
    }
}