package com.mahan.compose.jettodo.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mahan.compose.jettodo.data.models.Priority
import com.mahan.compose.jettodo.ui.theme.LARGE_PADDING
import com.mahan.compose.jettodo.ui.theme.PRIORITY_INDICATOR_SIZE

@Composable
fun FilterDropDownMenu(
    expanded: Boolean,
    onDismissRequest: (Boolean) -> Unit,
    onMenuItemClicked: (Priority) -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismissRequest(false) }
    ) {
        DropdownMenuItem(
            onClick = {
                onMenuItemClicked(Priority.Low)
                onDismissRequest(false)
            }
        ) {
            PriorityItem(priority = Priority.Low)
        }

        DropdownMenuItem(
            onClick = {
                onMenuItemClicked(Priority.High)
                onDismissRequest(false)
            }
        ) {
            PriorityItem(priority = Priority.High)
        }

        DropdownMenuItem(
            onClick = {
                onMenuItemClicked(Priority.None)
                onDismissRequest(false)
            }
        ) {
            PriorityItem(priority = Priority.None)
        }

    }
}


@Composable
fun PriorityItem(priority: Priority) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        PriorityIndicator(
            circleSize = PRIORITY_INDICATOR_SIZE,
            priority = priority
        )
        Text(
            modifier = Modifier.padding(start = LARGE_PADDING),
            text = priority.name,
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onSurface
        )
    }
}

@Preview
@Composable
fun PriorityItemPreview() {
    PriorityItem(priority = Priority.Low)
}