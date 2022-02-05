package com.mahan.compose.jettodo.ui.screens

import android.util.Log
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
import com.mahan.compose.jettodo.data.models.TodoTask
import com.mahan.compose.jettodo.ui.components.ListFab
import com.mahan.compose.jettodo.ui.components.ListTopAppBar
import com.mahan.compose.jettodo.ui.components.TaskItem
import com.mahan.compose.jettodo.ui.theme.MediumGray
import com.mahan.compose.jettodo.ui.viewmodels.SharedViewModel
import com.mahan.compose.jettodo.util.Action
import com.mahan.compose.jettodo.util.RequestState
import com.mahan.compose.jettodo.util.SearchAppBarState
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun ListScreen(
    //action: Action,
    navigateToTaskScreen: (Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllTasks()
    }


    val tasks by sharedViewModel.tasks.collectAsState()
    val searchedTasks by sharedViewModel.searchedTasks.collectAsState()

    val searchAppBarState by sharedViewModel.searchAppBarState

    val searchText: String by sharedViewModel.searchAppBarText

    val action by sharedViewModel.action

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
                searchAppBarText = searchText
            )
        },
        floatingActionButton = {
            ListFab(onFabClicked = navigateToTaskScreen)
        }
    ) {
        HandleListContent(
            allTasks = tasks,
            searchedTasks = searchedTasks,
            navigateToTaskScreen = navigateToTaskScreen,
            searchAppBarState = searchAppBarState
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun HandleListContent(
    allTasks: RequestState<List<TodoTask>>,
    searchedTasks: RequestState<List<TodoTask>>,
    navigateToTaskScreen: (Int) -> Unit,
    searchAppBarState: SearchAppBarState
) {
    if (searchAppBarState == SearchAppBarState.TRIGGERED) {
        if (searchedTasks is RequestState.Success) {
            ListContent(tasks = searchedTasks.data, navigateToTaskScreen = navigateToTaskScreen)
        }
    } else {
        if (allTasks is RequestState.Success) {
            ListContent(tasks = allTasks.data, navigateToTaskScreen = navigateToTaskScreen)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun ListContent(
    tasks: List<TodoTask>,
    navigateToTaskScreen: (Int) -> Unit
) {
    LazyColumn {
        items(
            items = tasks,
            key = { it.id }
        ) {
            TaskItem(todoTask = it, navigateToTaskScreen = navigateToTaskScreen)
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
    onUndoClicked: (Action) -> Unit
) {
    
    val scope  = rememberCoroutineScope()
    
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION)
            scope.launch {
                val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = "${action.name}: $tittle",
                    actionLabel = if (action == Action.DELETE) "Undo" else "Ok"
                )
                undoDeletedTask(
                    snackBarResult = snackBarResult,
                    action = action,
                    onUndoClicked = onUndoClicked
                )
            }
    }
}

private fun undoDeletedTask(
    snackBarResult: SnackbarResult,
    action: Action,
    onUndoClicked: (Action) -> Unit
) {
    if (snackBarResult == SnackbarResult.ActionPerformed && action == Action.DELETE){
        onUndoClicked(Action.UNDO)
    }
}