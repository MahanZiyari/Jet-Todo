package com.mahan.compose.jettodo.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mahan.compose.jettodo.R

@Composable
fun ListFab(
    onFabClicked: (Int) -> Unit
) {
    FloatingActionButton(
        onClick = { onFabClicked(-1) }
    ) {
        Icon(
            modifier = Modifier.size(40.dp),
            imageVector = Icons.Rounded.Add,
            contentDescription = stringResource(id = R.string.add_button),
            tint = Color.White
        )
    }
}

@Preview
@Composable
fun ListFabPreview() {
    ListFab(onFabClicked = {})
}






