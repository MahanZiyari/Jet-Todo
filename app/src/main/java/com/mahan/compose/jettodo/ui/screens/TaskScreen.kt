package com.mahan.compose.jettodo.ui.screens

import androidx.compose.runtime.Composable
import com.mahan.compose.jettodo.data.models.TodoTask
import com.mahan.compose.jettodo.ui.components.appbars.TaskScreenAppBar
import com.mahan.compose.jettodo.util.Action

@Composable
fun TaskScreen(
    selectedTask: TodoTask?,
    navigateToListScreen: (Action) -> Unit
) {
    TaskScreenAppBar(selectedTask = selectedTask, navigateToListScreen = navigateToListScreen)
}



