package com.mahan.compose.jettodo.ui.components.buttons

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.mahan.compose.jettodo.R
import com.mahan.compose.jettodo.data.models.Priority
import com.mahan.compose.jettodo.ui.components.dropdownmenus.SortDropDownMenu


import com.mahan.compose.jettodo.ui.theme.MEDIUM_PADDING

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

@Composable
fun SearchAction(
    onSearchClicked: () -> Unit
) {
    IconButton(
        onClick = { onSearchClicked() }
    ) {
        Icon(
            imageVector = Icons.Rounded.Search,
            contentDescription = stringResource(R.string.search_action),
            tint = MaterialTheme.colors.onPrimary
        )
    }

}

@Composable
fun SortAction(
    onPriorityItemClicked: (Priority) -> Unit
) {
    // States
    var expanded by remember {
        mutableStateOf(false)
    }

    // UI
    IconButton(
        onClick = {
            expanded = true
        }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_filter),
            contentDescription = stringResource(id = R.string.filter_action),
            tint = MaterialTheme.colors.onPrimary
        )
    }

    SortDropDownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = it },
        onMenuItemClicked = onPriorityItemClicked
    )

}

@Composable
fun MenuAction(
    onDeleteClicked: () -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    IconButton(
        onClick = {
            expanded = true
        }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_kebab),
            contentDescription = stringResource(R.string.more_action),
            tint = MaterialTheme.colors.onPrimary
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        DropdownMenuItem(
            onClick = {
                onDeleteClicked()
                expanded = false
            }
        ) {
            Text(
                modifier = Modifier
                    .padding(start = MEDIUM_PADDING),
                text = stringResource(R.string.delete_all_action),
                style = MaterialTheme.typography.subtitle2
            )
        }
    }
}