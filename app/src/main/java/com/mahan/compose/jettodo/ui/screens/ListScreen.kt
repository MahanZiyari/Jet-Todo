package com.mahan.compose.jettodo.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mahan.compose.jettodo.R
import com.mahan.compose.jettodo.data.models.Priority
import com.mahan.compose.jettodo.data.models.TodoTask
import com.mahan.compose.jettodo.ui.components.ListFab
import com.mahan.compose.jettodo.ui.components.RedBackground
import com.mahan.compose.jettodo.ui.components.TaskItem
import com.mahan.compose.jettodo.ui.components.appbars.ListTopAppBar
import com.mahan.compose.jettodo.ui.theme.MediumGray
import com.mahan.compose.jettodo.ui.viewmodels.SharedViewModel
import com.mahan.compose.jettodo.util.Action
import com.mahan.compose.jettodo.util.RequestState
import com.mahan.compose.jettodo.util.SearchAppBarState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun ListScreen(
    navigateToTaskScreen: (Int) -> Unit,
    sharedViewModel: SharedViewModel
) {

    val tasks by sharedViewModel.tasks.collectAsState()
    val searchedTasks by sharedViewModel.searchedTasks.collectAsState()

    val searchAppBarState by sharedViewModel.searchAppBarState

    val searchText: String by sharedViewModel.searchAppBarText

    val action by sharedViewModel.action

    val sortState by sharedViewModel.sortState.collectAsState()
    val tasksSortByLowPriority by sharedViewModel.lowPriorityTasks.collectAsState()
    val tasksSortByHighPriority by sharedViewModel.highPriorityTasks.collectAsState()

    val scaffoldState = rememberScaffoldState()


    DisplaySnackBar(
        scaffoldState = scaffoldState,
        tittle = sharedViewModel.title.value,
        action = action,
        onUndoClicked = {
            sharedViewModel.handleDatabaseActions(it)
        }
    )

    Scaffold(
        modifier = Modifier,
        scaffoldState = scaffoldState,
        topBar = {
            ListTopAppBar(
                sharedViewModel = sharedViewModel,
                searchAppBarState = searchAppBarState,
                searchAppBarText = searchText,
            )
        },
        floatingActionButton = {
            ListFab(onFabClicked = navigateToTaskScreen)
        }
    ) {
        HandleListContent(
            allTasks = tasks,
            searchedTasks = searchedTasks,
            sortState = sortState,
            tasksSortedByLowPriority = tasksSortByLowPriority,
            tasksSortedByHighPriority = tasksSortByHighPriority,
            navigateToTaskScreen = navigateToTaskScreen,
            searchAppBarState = searchAppBarState,
            onSwipeToDelete = { action, task ->
                sharedViewModel.updateTaskContentFields(task)
                sharedViewModel.handleDatabaseActions(action)
            }
        )
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun HandleListContent(
    allTasks: RequestState<List<TodoTask>>,
    searchedTasks: RequestState<List<TodoTask>>,
    sortState: RequestState<Priority>,
    tasksSortedByLowPriority: List<TodoTask>,
    tasksSortedByHighPriority: List<TodoTask>,
    navigateToTaskScreen: (Int) -> Unit,
    onSwipeToDelete: (Action, TodoTask) -> Unit,
    searchAppBarState: SearchAppBarState
) {
    if (sortState !is RequestState.Success) return
    when {
        searchAppBarState == SearchAppBarState.TRIGGERED -> {
            if (searchedTasks is RequestState.Success) {
                ListContent(
                    tasks = searchedTasks.data,
                    navigateToTaskScreen = navigateToTaskScreen,
                    onSwipeToDelete = onSwipeToDelete
                )
            }
        }
        sortState.data == Priority.None -> {
            if (allTasks is RequestState.Success) {
                if (allTasks.data.isEmpty())
                    EmptyContent()
                ListContent(
                    tasks = allTasks.data,
                    navigateToTaskScreen = navigateToTaskScreen,
                    onSwipeToDelete = onSwipeToDelete
                )
            }
        }
        sortState.data == Priority.Low -> {
            ListContent(
                tasks = tasksSortedByLowPriority,
                navigateToTaskScreen = navigateToTaskScreen,
                onSwipeToDelete = onSwipeToDelete
            )
        }
        sortState.data == Priority.High -> {
            ListContent(
                tasks = tasksSortedByHighPriority,
                navigateToTaskScreen = navigateToTaskScreen,
                onSwipeToDelete = onSwipeToDelete
            )
        }
    }

}

@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun ListContent(
    tasks: List<TodoTask>,
    navigateToTaskScreen: (Int) -> Unit,
    onSwipeToDelete: (Action, TodoTask) -> Unit
) {
    LazyColumn {
        items(
            items = tasks,
            key = { it.id }
        ) {
            val dismissState = rememberDismissState()
            val degree by animateFloatAsState(
                targetValue = if (dismissState.targetValue == DismissValue.Default) 0f else -45f
            )
            val dismissDirection = dismissState.dismissDirection
            // Whether the component has been dismissed in the given direction.
            //Params: direction - The dismiss direction.
            val isDismissed = dismissState.isDismissed(
                DismissDirection.EndToStart
            )

            if (isDismissed && dismissDirection == DismissDirection.EndToStart) {
                val scope = rememberCoroutineScope()
                scope.launch {
                    delay(300)
                    onSwipeToDelete(Action.DELETE, it)
                }
            }

            var itemAppeared by remember {
                mutableStateOf(false)
            }
            LaunchedEffect(key1 = true) {
                itemAppeared = true
            }

            AnimatedVisibility(
                visible = itemAppeared && !isDismissed,
                enter = expandVertically(
                    animationSpec = tween(
                        durationMillis = 300
                    )
                ),
                exit = shrinkVertically(
                    animationSpec = tween(
                        durationMillis = 300
                    )
                )
            ) {
                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    dismissThresholds = { FractionalThreshold(0.2f) },
                    background = { RedBackground(degree = degree) },
                    dismissContent = {
                        TaskItem(todoTask = it, navigateToTaskScreen = navigateToTaskScreen)
                    }
                )
            }
        }
    }
}

@Composable
fun EmptyContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(120.dp),
            painter = painterResource(id = R.drawable.ic_sad_face),
            contentDescription = "Empty Database",
            tint = MediumGray
        )
        Text(
            text = "No Tasks Found.",
            fontSize = MaterialTheme.typography.h6.fontSize,
            fontWeight = FontWeight.Bold,
            color = MediumGray
        )
    }
}

@Composable
fun DisplaySnackBar(
    scaffoldState: ScaffoldState,
    tittle: String,
    action: Action,
    onUndoClicked: (Action) -> Unit,
    onComplete: (Action) -> Unit = {}
) {

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                Log.d("Action", "DisplaySnackBar: $action")
                val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = if (action == Action.DELETE_ALL) "All Tasks Removed." else "${action.name}: $tittle",
                    actionLabel = if (action == Action.DELETE) "Undo" else "Ok"
                )
                undoDeletedTask(
                    snackBarResult = snackBarResult,
                    action = action,
                    onUndoClicked = onUndoClicked
                )
            }
            onComplete(Action.NO_ACTION)
        }
    }
}

private fun undoDeletedTask(
    snackBarResult: SnackbarResult,
    action: Action,
    onUndoClicked: (Action) -> Unit
) {
    if (snackBarResult == SnackbarResult.ActionPerformed && action == Action.DELETE) {
        onUndoClicked(Action.UNDO)
    }
}