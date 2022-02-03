package com.mahan.compose.jettodo.ui.components.appbars

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.mahan.compose.jettodo.data.models.TodoTask
import com.mahan.compose.jettodo.util.Action

@Composable
fun TaskScreenAppBar(
    selectedTask: TodoTask?,
    navigateToListScreen: (Action) -> Unit
) {
    if (selectedTask == null)
        NewTaskAppBar(navigateToListScreen = navigateToListScreen)
    else
        ExistTaskAppBar(selectedTask = selectedTask, navigateToListScreen = navigateToListScreen)
}

@Composable
fun NewTaskAppBar(
    navigateToListScreen: (Action) -> Unit
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primaryVariant,
        navigationIcon = {
            IconButton(onClick = { navigateToListScreen(Action.NO_ACTION) }) {
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
            IconButton(onClick = { navigateToListScreen(Action.ADD) }) {
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
    NewTaskAppBar(navigateToListScreen = {})
}

@Composable
fun ExistTaskAppBar(
    selectedTask: TodoTask,
    navigateToListScreen: (Action) -> Unit
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primaryVariant,
        navigationIcon = {
            IconButton(onClick = { navigateToListScreen(Action.NO_ACTION) }) {
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
            IconButton(onClick = { navigateToListScreen(Action.DELETE) }) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = "Add Task",
                    tint = MaterialTheme.colors.onPrimary
                )
            }

            IconButton(onClick = { navigateToListScreen(Action.UPDATE) }) {
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
fun ExistTaskAppBarPreview() {
    ExistTaskAppBar(selectedTask = TodoTask(), navigateToListScreen = {})
}