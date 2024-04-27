package com.example.cs501finalproject.ui

import androidx.compose.material.Colors
import androidx.compose.material.lightColors
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color


enum class ThemeState { Pink, Yellow, Green, Blue }

var currThemeState by mutableStateOf(ThemeState.Pink)

fun getAppThemeColors(theme: ThemeState): Colors = when (theme) {
    ThemeState.Pink -> PinkThemeColors
    ThemeState.Yellow -> YellowThemeColors
    ThemeState.Green -> GreenThemeColors
    ThemeState.Blue -> BlueThemeColors
}


val PinkThemeColors = lightColors(
    primary = Color(0xffDECCE4),
    primaryVariant = Color(0xFFAF97B8),
    secondary = Color(0xFFF8BBD0)
)

val BlueThemeColors = lightColors(
    primary = Color(0xFF2196F3),
    primaryVariant = Color(0xFF1976D2),
    secondary = Color(0xFF90CAF9)
)

val GreenThemeColors = lightColors(
    primary = Color(0xFF4CAF50),
    primaryVariant = Color(0xFF388E3C),
    secondary = Color(0xFFC8E6C9)
)

val YellowThemeColors = lightColors(
    primary = Color(0xFFFFEB3B),
    primaryVariant = Color(0xFFFBC02D),
    secondary = Color(0xFFFFF9C4)
)
