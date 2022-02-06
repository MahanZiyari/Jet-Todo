package com.mahan.compose.jettodo.navigation


import android.transition.Transition
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.mahan.compose.jettodo.ui.screens.ListScreen
import com.mahan.compose.jettodo.ui.screens.SplashScreen
import com.mahan.compose.jettodo.ui.screens.TaskScreen
import com.mahan.compose.jettodo.ui.viewmodels.SharedViewModel
import com.mahan.compose.jettodo.util.Action

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun SetupNavigation(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {

    val navigateToListScreen: (Action) -> Unit = {
        navController.navigate(route = Destination.ListScreen.name + "/${it.name}") {
            popUpTo(0)
        }
    }

    val navigateToTaskScreen: (Int) -> Unit = {
        navController.navigate(route = Destination.TaskScreen.name + "/$it")
    }

    AnimatedNavHost(
        navController = navController,
        startDestination = Destination.SplashScreen.name
    ) {
        composable(
            route = Destination.SplashScreen.name,
            exitTransition = {
                slideOutVertically(
                    animationSpec = tween(
                        durationMillis = 2000
                    )
                )
            }
        ) {
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
                navigateToTaskScreen = navigateToTaskScreen,
                sharedViewModel = sharedViewModel
            )
        }

        // TaskScreen
        composable(
            route = Destination.TaskScreen.name + "/{taskId}",
            arguments = listOf(navArgument(name = "taskId") { type = NavType.IntType }),
            enterTransition = {
                slideInHorizontally(
                    animationSpec = tween(500),
                    initialOffsetX = { it }
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(1000)
                )
            }
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