package com.mahan.compose.jettodo.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ListTopAppBar() {
    DefaultListTopAppBar()
}

@Preview
@Composable
fun ListTopAppBarPreview() {
    ListTopAppBar()
}

@Composable
fun DefaultListTopAppBar() {
    TopAppBar(
        title = { Text(text = "Tasks", color = MaterialTheme.colors.onPrimary) },
        backgroundColor = MaterialTheme.colors.primaryVariant
    )
}