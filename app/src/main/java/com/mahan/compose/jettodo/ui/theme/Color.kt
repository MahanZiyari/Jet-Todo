package com.mahan.compose.jettodo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val LightGray = Color(0xFFFCFCFC)
val MediumGray = Color(0xFF9C9C9C)
val DarkGray = Color(0xFF141414)

val HighPriorityColor = Color(0xFFFF4646)
val MediumPriorityColor = Color(0xFFFFC114)
val LowPriorityColor = Color(0xFF00C980)
val NonePriorityColor = MediumGray

val Colors.taskItemTextColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) LightGray else DarkGray

val Colors.taskItemBackGroundColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) DarkGray else Color.White

val topAppBarBackgroundColor: Color
@Composable
get() = if (isSystemInDarkTheme()) Color.Black else Purple700