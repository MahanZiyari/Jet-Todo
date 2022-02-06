package com.mahan.compose.jettodo.ui.components.dropdownmenus

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.mahan.compose.jettodo.data.models.Priority
import com.mahan.compose.jettodo.ui.components.PriorityIndicator
import com.mahan.compose.jettodo.ui.components.PriorityItem
import com.mahan.compose.jettodo.ui.theme.PRIORITY_INDICATOR_SIZE

@Composable
fun PrioritySelection(
    modifier: Modifier = Modifier,
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    var parentSize by remember {
        mutableStateOf(IntSize.Zero)
    }

    val angle by animateFloatAsState(targetValue = if (expanded) 180f else 0f)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .onGloballyPositioned {
                parentSize = it.size
            }
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
                shape = MaterialTheme.shapes.small
            )
            .clickable {
                expanded = true
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        PriorityIndicator(
            modifier = Modifier.weight(1f),
            circleSize = PRIORITY_INDICATOR_SIZE,
            priority = priority
        )

        Text(
            modifier = Modifier
                .weight(8f),
            text = priority.name,
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onSurface
        )

        Icon(
            modifier = Modifier
                .weight(1f)
                .rotate(angle),
            imageVector = Icons.Rounded.ArrowDropDown,
            contentDescription = "Expansion Icon",
            tint = MaterialTheme.colors.onSurface
        )


        // DropDownMenu
        DropdownMenu(
            modifier = Modifier
                .width(with(LocalDensity.current) { parentSize.width.toDp() }),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Priority.values().reversedArray().slice(1..3).forEach {
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onPrioritySelected(it)
                    }
                ) {
                    PriorityItem(priority = it)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrioritySelectionPreview() {
    PrioritySelection(priority = Priority.Medium, onPrioritySelected = {})
}