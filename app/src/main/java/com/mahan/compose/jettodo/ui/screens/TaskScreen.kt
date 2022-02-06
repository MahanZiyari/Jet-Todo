package com.mahan.compose.jettodo.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mahan.compose.jettodo.data.models.Priority
import com.mahan.compose.jettodo.data.models.TodoTask
import com.mahan.compose.jettodo.ui.components.appbars.TaskScreenAppBar
import com.mahan.compose.jettodo.ui.components.dropdownmenus.PrioritySelection
import com.mahan.compose.jettodo.ui.theme.LARGE_PADDING
import com.mahan.compose.jettodo.ui.viewmodels.SharedViewModel
import com.mahan.compose.jettodo.util.Action
import com.mahan.compose.jettodo.util.displayToast

@Composable
fun TaskScreen(
    selectedTask: TodoTask?,
    sharedViewModel: SharedViewModel,
    navigateToListScreen: (Action) -> Unit
) {

    BackHandler(enabled = true) {
        sharedViewModel.handleDatabaseActions(Action.NO_ACTION)
        navigateToListScreen(Action.NO_ACTION)
    }

    val title by sharedViewModel.title
    val description by sharedViewModel.description
    val priority by sharedViewModel.priority

    val context = LocalContext.current
    Scaffold(
        topBar = {
            TaskScreenAppBar(
                selectedTask = selectedTask,
                navigateToListScreen = { action ->
                    when(action) {
                        Action.NO_ACTION -> navigateToListScreen(action)
                        else -> {
                            if (sharedViewModel.validateFields())
                                navigateToListScreen(action)
                            else
                                displayToast(context, "Fields Empty.")
                        }
                    }
                },
                handleDatabaseAction = {
                    sharedViewModel.handleDatabaseActions(it)
                },
                updateSelectedTask = {
                    sharedViewModel.updateTaskContentFields(it)
                }
            )
        },
        backgroundColor = MaterialTheme.colors.background
    ) {
        Content(
            title = title,
            onTitleChange = {
                sharedViewModel.updateTitle(it)
            },
            description = description,
            onDescriptionChange = {
                sharedViewModel.updateDescription(it)
            },
            priority = priority,
            onPrioritySelected = {
                sharedViewModel.updatePriority(it)
            }
        )
    }

}


@Composable
private fun Content(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(LARGE_PADDING)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = { onTitleChange(it) },
            singleLine = true,
            label = { Text(text = "Title") },
            textStyle = MaterialTheme.typography.body1
        )

        Spacer(modifier = Modifier.height(10.dp))

        PrioritySelection(
            priority = priority,
            onPrioritySelected = onPrioritySelected
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxSize(),
            value = description,
            onValueChange = { onDescriptionChange(it) },
            label = { Text(text = "Description") },
            textStyle = MaterialTheme.typography.body1
        )
    }
}

@Preview
@Composable
private fun ContentPreview() {
    Content(
        title = "",
        onTitleChange = {},
        description = "",
        onDescriptionChange = {},
        priority = Priority.Low,
        onPrioritySelected = {}
    )
}



