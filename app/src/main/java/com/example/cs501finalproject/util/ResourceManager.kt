package com.example.cs501finalproject.util

//import android.content.Context
//import android.content.res.Resources
//import java.util.Locale
//
//object ResourceManager {
//    private var resources: Resources? = null
//    private var currentLocale: Locale = Locale.getDefault()
//
//    fun updateResources(context: Context, locale: Locale) {
//        val configuration = context.resources.configuration
//        configuration.setLocale(locale)
//        currentLocale = locale
//        resources = context.createConfigurationContext(configuration).resources
//    }
//
//    fun getString(context: Context, id: Int): String {
//        updateIfNecessary(context)
//        return resources?.getString(id) ?: "Undefined"
//    }
//
//    private fun updateIfNecessary(context: Context) {
//        if (resources == null || currentLocale != Locale.getDefault()) {
//            updateResources(context, currentLocale)
//        }
//    }
//}
