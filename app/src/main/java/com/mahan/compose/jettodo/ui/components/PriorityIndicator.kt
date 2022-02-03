package com.mahan.compose.jettodo.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.mahan.compose.jettodo.data.models.Priority
import com.mahan.compose.jettodo.ui.theme.PRIORITY_INDICATOR_SIZE

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