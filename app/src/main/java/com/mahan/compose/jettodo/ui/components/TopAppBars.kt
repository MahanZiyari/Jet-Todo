package com.mahan.compose.jettodo.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mahan.compose.jettodo.data.models.Priority

@Composable
fun ListTopAppBar() {
    DefaultListTopAppBar(
        onSearchClicked = {},
        onPriorityItemClicked = {},
        onDeleteClicked = {}
    )
}

@Preview
@Composable
fun ListTopAppBarPreview() {
    ListTopAppBar()
}

@Composable
fun DefaultListTopAppBar(
    onSearchClicked: () -> Unit,
    onPriorityItemClicked: (Priority) -> Unit,
    onDeleteClicked: () -> Unit
) {
    TopAppBar(
        title = { Text(text = "Tasks", color = MaterialTheme.colors.onPrimary) },
        backgroundColor = MaterialTheme.colors.primaryVariant,
        actions = {
            DefaultAppBarActions(
                onSearchClicked = onSearchClicked,
                onPriorityItemClicked = onPriorityItemClicked,
                onDeleteClicked = onDeleteClicked
            )
        }
    )
}

@Composable
fun DefaultAppBarActions(
    onSearchClicked: () -> Unit,
    onPriorityItemClicked: (Priority) -> Unit,
    onDeleteClicked: () -> Unit
) {
    SearchAction(onSearchClicked = onSearchClicked)
    SortAction(onPriorityItemClicked = onPriorityItemClicked)
    MenuAction(onDeleteClicked = onDeleteClicked)
}