package com.example.cs501finalproject.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cs501finalproject.R
import com.example.cs501finalproject.util.LanguageManager
import com.example.cs501finalproject.util.ThemeManager
import java.util.Locale

@Composable
fun SettingsLanguagePage(navController: NavController, languageManager: LanguageManager) {
    val colors = ThemeManager.getAppThemeColors()
    val context = LocalContext.current
    val currentLocale = languageManager.getCurrentLocale()
    var selectedLanguage by remember { mutableStateOf(
        when (currentLocale) {
            Locale.ENGLISH -> "English"
            Locale.SIMPLIFIED_CHINESE -> "中文 (Chinese)"
            Locale.FRENCH -> "Français (French)"
            Locale("es") -> "Español (Spanish)"
            Locale("ru") -> "Русский (Russian)"
            Locale("he", "IL") -> "עברית (Hebrew)"
            else -> "English"
        }
    )}
    val languages = listOf(
        "English",
        "中文 (Chinese)",
        "Français (French)",
        "Español (Spanish)",
        "Русский (Russian)",
        "עברית (Hebrew)"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.Settings_Language_SelectLanguage)) },
                backgroundColor = colors.secondary,
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Button(
                        onClick = {
                            val newLocale = when (selectedLanguage) {
                                "English" -> Locale("en", "US")
                                "中文 (Chinese)" -> Locale("zh", "CN")
                                "Français (French)" -> Locale("fr", "FR")
                                "Español (Spanish)" -> Locale("es", "ES")
                                "Русский (Russian)" -> Locale("ru", "RU")
                                "עברית (Hebrew)" -> Locale("he", "IL")
                                else -> Locale.getDefault()
                            }
                            languageManager.setLocale(context, newLocale)
                            //navController.navigateUp()
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = colors.secondaryVariant)
                    ) {
                        Text(stringResource(R.string.Settings_Confirm))
                    }
                }
            )
        },
        backgroundColor = colors.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top) {
            languages.forEach { language ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedLanguage == language,
                        onClick = { selectedLanguage = language }
                    )
                    Text(
                        text = language,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        fontSize = 18.sp
                    )
                    if (selectedLanguage == language) {
                        Icon(
                            Icons.Filled.Check,
                            contentDescription = "Selected",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}