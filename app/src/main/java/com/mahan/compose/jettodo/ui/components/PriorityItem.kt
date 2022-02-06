package com.mahan.compose.jettodo.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.mahan.compose.jettodo.data.models.Priority
import com.mahan.compose.jettodo.ui.theme.LARGE_PADDING
import com.mahan.compose.jettodo.ui.theme.PRIORITY_INDICATOR_SIZE

@Composable
fun PriorityItem(
    modifier: Modifier = Modifier,
    priority: Priority
) {
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

@Composable
fun PriorityIndicator(
    modifier: Modifier = Modifier,
    circleSize: Dp,
    priority: Priority
) {
    Canvas(
        modifier = modifier
            .size(circleSize)
    ) {
        drawCircle(color = priority.color)
    }
}

@Preview
@Composable
fun PriorityIndicatorPreview() {
    PriorityIndicator(
        circleSize = PRIORITY_INDICATOR_SIZE,
        priority = Priority.Low
    )
}