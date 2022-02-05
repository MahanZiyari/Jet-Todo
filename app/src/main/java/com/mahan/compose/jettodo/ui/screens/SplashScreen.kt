package com.mahan.compose.jettodo.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mahan.compose.jettodo.R
import com.mahan.compose.jettodo.ui.theme.splashScreenColor

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.splashScreenColor)
        ,
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .size(100.dp),
            painter = painterResource(id = getLogo()),
            contentDescription = "Logo"
        )
    }
}

@Composable
fun getLogo(): Int = if (isSystemInDarkTheme()) R.drawable.ic_logo_dark else R.drawable.ic_logo_light

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}

@Preview (uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SplashScreenPreviewDarkMode() {
    SplashScreen()
}
