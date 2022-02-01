package com.mahan.compose.jettodo.ui.screens

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.mahan.compose.jettodo.ui.components.ListFab
import com.mahan.compose.jettodo.ui.components.ListTopAppBar
import com.mahan.compose.jettodo.ui.viewmodels.SharedViewModel

@Composable
fun ListScreen(
    navigateToTaskScreen: (Int) -> Unit,
    sharedViewModel: SharedViewModel
) {

    val searchAppBarState by sharedViewModel.searchAppBarState

    val searchText: String by sharedViewModel.searchAppBarText
    Scaffold(
        modifier = Modifier,
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

    }
}