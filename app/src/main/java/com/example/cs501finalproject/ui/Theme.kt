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
    primary = Color(0xffE6DCE7),
    primaryVariant = Color(0xFFC6B0C0),
    secondary = Color(0xFFF8BBD0)
)

val BlueThemeColors = lightColors(
    primary = Color(0xFFB2FBFE),
    primaryVariant = Color(0xFF87EEFD),
    secondary = Color(0xFF90CAF9)
)

val GreenThemeColors = lightColors(
    primary = Color(0xFFA8F7A9),
    primaryVariant = Color(0xFF70F774),
    secondary = Color(0xFFC8E6C9)
)

val YellowThemeColors = lightColors(
    primary = Color(0xFFFFFDA8),
    primaryVariant = Color(0xFFE3DA7B),
    secondary = Color(0xFFFFF9C4)
)
