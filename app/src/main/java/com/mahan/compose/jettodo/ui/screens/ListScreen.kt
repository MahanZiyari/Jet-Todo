package com.mahan.compose.jettodo.ui.screens

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mahan.compose.jettodo.ui.components.ListFab
import com.mahan.compose.jettodo.ui.components.ListTopAppBar

@Composable
fun ListScreen(
    navigateToTaskScreen: (Int) -> Unit
) {
    Scaffold(
        modifier = Modifier,
        topBar = {
            ListTopAppBar()
        },
        floatingActionButton = {
            ListFab(onFabClicked = navigateToTaskScreen)
        }
    ) {

    }
}


@Preview
@Composable
fun ListScreenPreview() {
    ListScreen(navigateToTaskScreen = {})
}