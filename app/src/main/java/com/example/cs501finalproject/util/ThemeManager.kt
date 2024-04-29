package com.example.cs501finalproject.util

import androidx.compose.material.Colors
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object ThemeManager {
    private val listeners = mutableSetOf<() -> Unit>()
    var currentTheme: ThemeState by mutableStateOf(ThemeState.Pink)

    val themes = mapOf(
        ThemeState.Pink to PinkThemeColors,
        ThemeState.Amber to AmberThemeColors,
        ThemeState.Green to GreenThemeColors,
        ThemeState.Blue to BlueThemeColors
    )

    fun getThemeColors(theme: ThemeState): Colors = themes[theme] ?: error("Theme not found")

    fun getAppThemeColors(): Colors = themes[currentTheme]!!

    fun onThemeChanged(newTheme: ThemeState) {
        if (currentTheme != newTheme) {
            currentTheme = newTheme
            listeners.forEach { it.invoke() }
        }
    }

    fun addThemeChangeListener(listener: () -> Unit) {
        listeners.add(listener)
    }

    fun removeThemeChangeListener(listener: () -> Unit) {
        listeners.remove(listener)
    }
}
