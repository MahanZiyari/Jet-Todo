package com.mahan.compose.jettodo.ui.components.dropdownmenus

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.runtime.Composable
import com.mahan.compose.jettodo.data.models.Priority
import com.mahan.compose.jettodo.ui.components.PriorityItem

@Composable
fun SortDropDownMenu(
    expanded: Boolean,
    onDismissRequest: (Boolean) -> Unit,
    onMenuItemClicked: (Priority) -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismissRequest(false) }
    ) {
        Priority.values().reversedArray().filterNot { it == Priority.Medium }.forEach {
            DropdownMenuItem(
                onClick = {
                    onMenuItemClicked(it)
                    onDismissRequest(false)
                }
            ) {
                PriorityItem(priority = it)
            }
        }

    }
}