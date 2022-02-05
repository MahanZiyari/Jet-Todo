package com.mahan.compose.jettodo.ui.components.appbars

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Delete
import com.mahan.compose.jettodo.R
import com.mahan.compose.jettodo.data.models.TodoTask
import com.mahan.compose.jettodo.ui.components.DisplayAlertDialog
import com.mahan.compose.jettodo.util.Action

@Composable
fun TaskScreenAppBar(
    selectedTask: TodoTask?,
    navigateToListScreen: (Action) -> Unit,
    handleDatabaseAction: (Action) -> Unit
) {
    if (selectedTask == null)
        NewTaskAppBar(
            navigateToListScreen = navigateToListScreen,
            handleDatabaseAction = handleDatabaseAction
        )
    else
        ExistTaskAppBar(
            selectedTask = selectedTask,
            navigateToListScreen = navigateToListScreen,
            handleDatabaseAction = handleDatabaseAction
        )
}

@Composable
fun NewTaskAppBar(
    navigateToListScreen: (Action) -> Unit,
    handleDatabaseAction: (Action) -> Unit
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primaryVariant,
        navigationIcon = {
            IconButton(
                onClick = {
                    handleDatabaseAction(Action.NO_ACTION)
                    navigateToListScreen(Action.NO_ACTION)
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "Back Button",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        },
        title = {
            Text(
                text = "New Task",
                color = MaterialTheme.colors.onPrimary
            )
        },
        actions = {
            IconButton(
                onClick = {
                    handleDatabaseAction(Action.ADD)
                    navigateToListScreen(Action.ADD)
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = "Add Task",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
    )
}

@Preview
@Composable
fun NewTaskAppBarPreview() {
    NewTaskAppBar(navigateToListScreen = {}, handleDatabaseAction = {})
}

@Composable
fun ExistTaskAppBar(
    selectedTask: TodoTask,
    navigateToListScreen: (Action) -> Unit,
    handleDatabaseAction: (Action) -> Unit
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primaryVariant,
        navigationIcon = {
            IconButton(
                onClick = {
                    handleDatabaseAction(Action.NO_ACTION)
                    navigateToListScreen(Action.NO_ACTION)
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Back Button",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        },
        title = {
            Text(
                text = selectedTask.title,
                color = MaterialTheme.colors.onPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            ExistTaskAppBarActions(
                selectedTask = selectedTask,
                navigateToListScreen = navigateToListScreen,
                handleDatabaseAction = handleDatabaseAction
            )
        }
    )
}

@Composable
private fun UpdateAction(
    handleDatabaseAction: (Action) -> Unit,
    navigateToListScreen: (Action) -> Unit
) {
    IconButton(
        onClick = {
            handleDatabaseAction(Action.UPDATE)
            navigateToListScreen(Action.UPDATE)
        }
    ) {
        Icon(
            imageVector = Icons.Rounded.Check,
            contentDescription = "Add Task",
            tint = MaterialTheme.colors.onPrimary
        )
    }
}

@Composable
private fun DeleteAction(
    onDeleteClicked: () -> Unit
) {
    IconButton(
        onClick = {
            onDeleteClicked()
        }
    ) {
        Icon(
            imageVector = Icons.Rounded.Delete,
            contentDescription = "Add Task",
            tint = MaterialTheme.colors.onPrimary
        )
    }
}

@Composable
fun ExistTaskAppBarActions(
    selectedTask: TodoTask,
    navigateToListScreen: (Action) -> Unit,
    handleDatabaseAction: (Action) -> Unit
) {
    var openDialog by remember {
        mutableStateOf(false)
    }

    DisplayAlertDialog(
        openDialog = openDialog,
        title = "Remove '${selectedTask.title}'?",
        message = "Are you sure to Remove '${selectedTask.title}'",
        confirmText = "Delete Task",
        cancelText = "Keep it",
        onDismissRequest = { openDialog = false },
        onConfirm = {
            handleDatabaseAction(Action.DELETE)
            navigateToListScreen(Action.DELETE)
        }
    )
    DeleteAction(
        onDeleteClicked = {
            openDialog = true
        }
    )

    UpdateAction(
        handleDatabaseAction = handleDatabaseAction,
        navigateToListScreen = navigateToListScreen
    )
}

@Preview
@Composable
fun ExistTaskAppBarPreview() {
    ExistTaskAppBar(
        selectedTask = TodoTask(),
        navigateToListScreen = {},
        handleDatabaseAction = {}
    )
}