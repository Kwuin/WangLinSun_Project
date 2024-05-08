package com.example.cs501finalproject.util

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import java.util.*

object LanguageManager {
    private var currentLocale = mutableStateOf(Locale.getDefault())

    fun initLanguage(context: Context) {
        // default: enable sync with system
        val autoSyncEnabled = isAutoSyncEnabled(context)
        if (autoSyncEnabled) {
            setLocale(context, Locale.getDefault()) // use system default language
        } else {
            val savedLocale = getSavedLocale(context)
            savedLocale?.let {
                setLocale(context, it) // use saved preferred language
            } ?: setLocale(context, Locale.ENGLISH) // if no saved preferred language, default -> English
        }
    }

    fun setLocale(context: Context, locale: Locale) {
        if (locale != currentLocale.value) {
            currentLocale.value = locale
            val configuration = context.resources.configuration
            configuration.setLocale(locale)
            context.createConfigurationContext(configuration)
            context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
            saveLocale(context, locale)
        }
    }

    fun getCurrentLocale(): Locale = currentLocale.value

    fun isAutoSyncEnabled(context: Context): Boolean {
        val prefs = context.getSharedPreferences("language_settings", Context.MODE_PRIVATE)
        return prefs.getBoolean("auto_sync", true)
    }

    fun saveAutoSyncSetting(context: Context, isEnabled: Boolean) {
        val prefs = context.getSharedPreferences("language_settings", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("auto_sync", isEnabled).apply()
    }

    private fun saveLocale(context: Context, locale: Locale) {
        val prefs = context.getSharedPreferences("language_settings", Context.MODE_PRIVATE)
        prefs.edit().putString("locale", locale.toLanguageTag()).apply()
    }

    private fun getSavedLocale(context: Context): Locale? {
        val prefs = context.getSharedPreferences("language_settings", Context.MODE_PRIVATE)
        return prefs.getString("locale", null)?.let { Locale.forLanguageTag(it) }
    }
}


