package com.example.cs501finalproject

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.cs501finalproject.ui.BlogView
import com.example.cs501finalproject.ui.CalendarPage
import com.example.cs501finalproject.ui.HomePage
import com.example.cs501finalproject.ui.MemoriesPage
import com.example.cs501finalproject.ui.NavigationBar
import com.example.cs501finalproject.ui.Profile
import com.example.cs501finalproject.ui.SettingsAboutPage
import com.example.cs501finalproject.ui.SettingsLanguagePage
import com.example.cs501finalproject.util.LanguageManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import java.util.UUID


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
    }

    @Composable
    fun MainApp(languageManager: LanguageManager) {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { NavigationBar(navController, Modifier, languageManager) }
        ) { innerPadding ->
            // 设置 NavHost 与 NavController，并应用由 Scaffold 提供的内边距
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(innerPadding)  // 应用内边距确保 NavHost 不与底部栏重叠
            ) {
                composable("home") { HomeScreen(navController) }
                composable("calendar") { CalendarScreen(navController) }
                composable("memories") { MemoriesScreen(navController) }
                composable("settings") { SettingsScreen(navController) }
                composable("settingsLanguage") {
                    val languageManager = remember { LanguageManager }
                    SettingsLanguageScreen(navController, languageManager)
                }
                composable("settingsAbout") { SettingsAboutScreen(navController) }
                composable(
                    "blog/{blogId}",
                    arguments = listOf(navArgument("blogId") { type = NavType.StringType })
                ) {backStackEntry ->

                    val blogIdString = backStackEntry.arguments?.getString("blogId") ?: "defaultBlogId"
                    val blogId = UUID.fromString(blogIdString)
                    // Pass the blogId to your BlogView
                    Log.d("Navigation Bar in", "new/${blogId}")
                    BlogView(navController, blogId)
                }
            }
        }
    }

//    @Composable
//    fun MainApp() {
//        val navController = rememberNavController()
//        Scaffold(
//            bottomBar = { NavigationBar(navController, Modifier) }
//        ) { innerPadding ->
//            // 设置 NavHost 与 NavController，并应用由 Scaffold 提供的内边距
//            NavHost(
//                navController = navController,
//                startDestination = "home",
//                modifier = Modifier.padding(innerPadding)  // 应用内边距确保 NavHost 不与底部栏重叠
//            ) {
//                composable("home") { HomeScreen(navController) }
//                composable("calendar") { CalendarScreen(navController) }
//                composable("memories") { MemoriesScreen(navController) }
//                composable("settings") { SettingsScreen(navController) }
//                composable("settingsLanguage") {
//                    val languageManager = remember { LanguageManager }
//                    SettingsLanguageScreen(navController, languageManager)
//                }
//                composable(
//                    "blog/{blogId}",
//                    arguments = listOf(navArgument("blogId") { type = NavType.StringType })
//                ) {backStackEntry ->
//
//                    val blogIdString = backStackEntry.arguments?.getString("blogId") ?: "defaultBlogId"
//                    val blogId = UUID.fromString(blogIdString)
//                    // Pass the blogId to your BlogView
//                    Log.d("Navigation Bar in", "new/${blogId}")
//                    BlogView(navController, blogId)
//                }
//            }
//        }
//    }


    @Composable
    fun HomeScreen(navController: NavController) {
        HomePage(navController)
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
    fun SettingsAboutScreen(navController: NavController) {
        SettingsAboutPage(navController)
    }

}