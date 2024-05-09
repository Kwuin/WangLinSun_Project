package com.example.cs501finalproject

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect

import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.cs501finalproject.ui.AtlasPage
import com.example.cs501finalproject.ui.BlogView
import com.example.cs501finalproject.ui.CalendarPage
import com.example.cs501finalproject.ui.HomePage
import com.example.cs501finalproject.ui.MapPage
import com.example.cs501finalproject.ui.MemoriesPage
import com.example.cs501finalproject.ui.NavigationBar
import com.example.cs501finalproject.ui.Profile
import com.example.cs501finalproject.ui.SettingsAboutPage
import com.example.cs501finalproject.ui.SettingsLanguagePage
import com.example.cs501finalproject.ui.SettingsNotificationsPage
import com.example.cs501finalproject.ui.SettingsThemePage
import com.example.cs501finalproject.util.LanguageManager
import com.example.cs501finalproject.util.ThemeManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import java.util.Objects
import java.util.UUID
import kotlin.math.sqrt


class MainActivity : AppCompatActivity() {

    private val coroutineScope: CoroutineScope = GlobalScope
//    private val languageManager by lazy { LanguageManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BlogRepository.initialize(this)
        val languageManager = LanguageManager
        languageManager.initLanguage(this)

        setContent {
            MainApp(languageManager)
        }

        updateSystemBarsColor()
        // Set the listener for theme changes
        ThemeManager.addThemeChangeListener {
            updateSystemBarsColor()
        }
    }



    @Composable
    fun MainApp(languageManager: LanguageManager) {

        val locationViewModel = LocationViewModel()

        val dateViewModel = DateViewModel()
        val navController = rememberNavController()
        val context = LocalContext.current
        val shakeDetector = remember {
            ShakeDetector(context) {
                navController.navigate("atlas")
            }
        }
        DisposableEffect(context) {
            shakeDetector.start()
            onDispose {
                shakeDetector.stop()
            }
        }

        Scaffold(
            bottomBar = { NavigationBar(navController, Modifier, languageManager) }
        ) { innerPadding ->
            // 设置 NavHost 与 NavController，并应用由 Scaffold 提供的内边距
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(innerPadding)  // 应用内边距确保 NavHost 不与底部栏重叠
            ) {
                composable("home") { HomeScreen(navController, dateViewModel) }
                composable("calendar") { CalendarScreen(navController) }
                composable("memories") { MemoriesScreen(navController) }
                composable("settings") { SettingsScreen(navController) }
                composable("map"){ MapScreen(navController, locationViewModel)}
                composable("atlas"){ AtlasPage(navController)}
                composable("settingsLanguage") {
                    val languageManager = remember { LanguageManager }
                    SettingsLanguageScreen(navController, languageManager)
                }
                composable("settingsTheme") { SettingsThemeScreen(navController) }
                composable("settingsNotifications") { SettingsNotificationsScreen(navController) }
                composable("settingsAbout") { SettingsAboutScreen(navController) }
                composable(
                    "blog/{blogId}",
                    arguments = listOf(navArgument("blogId") { type = NavType.StringType })
                ) {backStackEntry ->

                    val blogIdString = backStackEntry.arguments?.getString("blogId") ?: "defaultBlogId"
                    val blogId = UUID.fromString(blogIdString)
                    // Pass the blogId to your BlogView
                    Log.d("Navigation Bar in", "new/${blogId}")
                    BlogView(navController, blogId, locationViewModel)
                }
            }
        }
    }

    @Composable
    fun HomeScreen(navController: NavController, dateViewModel: DateViewModel) {
        HomePage(navController,dateViewModel)
    }

    @Composable
    fun CalendarScreen(navController: NavController) {
        CalendarPage(navController)
    }

    @Composable
    fun MemoriesScreen(navController: NavController) {
        MemoriesPage(navController)
    }

    @Composable
    fun SettingsScreen(navController: NavController) {
        Profile(navController)
    }

    @Composable
    fun SettingsLanguageScreen(navController: NavController, languageManager: LanguageManager) {
        SettingsLanguagePage(navController, languageManager)
    }

    @Composable
    fun SettingsThemeScreen(navController: NavController) {
        SettingsThemePage(navController)
    }

    @Composable
    fun SettingsNotificationsScreen(navController: NavController) {
        SettingsNotificationsPage(navController)
    }

    @Composable
    fun MapScreen(navController: NavController, locationViewModel: LocationViewModel){
        MapPage(navController, locationViewModel)
    }
    @Composable
    fun SettingsAboutScreen(navController: NavController) {
        SettingsAboutPage(navController)
    }

    private fun updateSystemBarsColor() {
        val colors = ThemeManager.getAppThemeColors()
        window.statusBarColor = colors.secondary.toArgb()
        window.navigationBarColor = colors.secondary.toArgb()
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        val languageManager = LanguageManager
//        languageManager.initLanguage(this)
//        val uuid = UUID.fromString(intent.getStringExtra("blog_id"))
//        setContent {
//            MainApp(languageManager)
//        }
//
//    }


}