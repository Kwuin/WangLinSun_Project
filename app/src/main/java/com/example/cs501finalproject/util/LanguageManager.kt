package com.example.cs501finalproject.util

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import java.util.*

object LanguageManager {
    private var currentLocale = mutableStateOf(Locale.ENGLISH)

    fun initLanguage(context: Context) {
        val savedLocale = getSavedLocale(context)
        savedLocale?.let {
            setLocale(context, it)
        }
    }

    fun setLocale(context: Context, locale: Locale) {
        if (locale != currentLocale.value) {
            currentLocale.value = locale
//            ResourceManager.updateResources(context, locale)
            val configuration = context.resources.configuration
            configuration.setLocale(locale)
            context.createConfigurationContext(configuration)
            context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
            saveLocale(context, locale)  // 保存选择的locale到SharedPreferences
        }
    }

    fun getCurrentLocale(): Locale {
        return currentLocale.value
    }


    private fun saveLocale(context: Context, locale: Locale) {
        val prefs = context.getSharedPreferences("language_settings", Context.MODE_PRIVATE)
        prefs.edit().putString("locale", locale.toString()).apply()
    }

    private fun getSavedLocale(context: Context): Locale? {
        val prefs = context.getSharedPreferences("language_settings", Context.MODE_PRIVATE)
        val localeString = prefs.getString("locale", null)
        return localeString?.let { Locale.forLanguageTag(it) }
    }
}
