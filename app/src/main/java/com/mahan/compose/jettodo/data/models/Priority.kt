package com.mahan.compose.jettodo.data.models

import androidx.compose.ui.graphics.Color
import com.mahan.compose.jettodo.ui.theme.HighPriorityColor
import com.mahan.compose.jettodo.ui.theme.LowPriorityColor
import com.mahan.compose.jettodo.ui.theme.MediumPriorityColor
import com.mahan.compose.jettodo.ui.theme.NonePriorityColor

enum class Priority(val color: Color) {
    High(HighPriorityColor),
    Medium(MediumPriorityColor),
    Low(LowPriorityColor),
    None(NonePriorityColor)
}