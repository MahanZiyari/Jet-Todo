package com.mahan.compose.jettodo.ui.screens

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun TaskScreen(taskId: Int?) {
    Text(
        text = taskId.toString(),
        style = MaterialTheme.typography.h2
    )
}