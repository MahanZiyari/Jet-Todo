package com.mahan.compose.jettodo.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.mahan.compose.jettodo.R
import com.mahan.compose.jettodo.data.models.Priority
import com.mahan.compose.jettodo.ui.theme.TOP_APP_BAR_HEIGHT
import com.mahan.compose.jettodo.ui.viewmodels.SharedViewModel
import com.mahan.compose.jettodo.util.SearchAppBarState

@Composable
fun ListTopAppBar(
    sharedViewModel: SharedViewModel,
    searchAppBarState: SearchAppBarState,
    searchAppBarText: String
) {
    when (searchAppBarState) {
        SearchAppBarState.CLOSED -> {
            DefaultListTopAppBar(
                onSearchClicked = {
                    sharedViewModel.searchAppBarState.value = SearchAppBarState.OPENED
                },
                onPriorityItemClicked = {},
                onDeleteClicked = {}
            )
        }

        else -> {
            SearchTopAppBar(
                text = searchAppBarText,
                onTextChange = {
                    sharedViewModel.searchAppBarText.value = it
                },
                onCloseClicked = {
                    if (searchAppBarText.isNotEmpty())
                        sharedViewModel.searchAppBarText.value = ""
                    else
                        sharedViewModel.searchAppBarState.value = SearchAppBarState.CLOSED
                },
                onSearchClicked = {}
            )
        }
    }

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


@Composable
fun SearchTopAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_APP_BAR_HEIGHT),
        color = MaterialTheme.colors.primaryVariant,
        elevation = AppBarDefaults.TopAppBarElevation
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = text,
            onValueChange = { onTextChange(it) },
            singleLine = true,
            placeholder = {
                Text(
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    text = "Search",
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    color = MaterialTheme.colors.onPrimary
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colors.onPrimary,
                backgroundColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colors.onPrimary
            ),
            leadingIcon = {
                IconButton(
                    modifier = Modifier.alpha(ContentAlpha.disabled),
                    onClick = {
                        onSearchClicked(text)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = stringResource(id = R.string.search_action),
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = { onCloseClicked() }) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = stringResource(R.string.close_search_bar),
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = { onSearchClicked(text) }
            )
        )
    }
}

@Preview
@Composable
fun SearchTopAppBarPreview() {
    SearchTopAppBar(
        text = "",
        onTextChange = {},
        onCloseClicked = {},
        onSearchClicked = {}
    )
}