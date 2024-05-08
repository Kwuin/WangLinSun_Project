package com.example.cs501finalproject.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cs501finalproject.util.LanguageManager
import com.example.cs501finalproject.util.ThemeManager
import java.util.Locale

@Composable
fun SettingsLanguagePage(navController: NavController, languageManager: LanguageManager) {
    val colors = ThemeManager.getAppThemeColors()
    val context = LocalContext.current
    val currentLocale = languageManager.getCurrentLocale()

    var selectedLanguage by remember { mutableStateOf(languageManager.getCurrentLocale().displayName) }
    var syncWithSystem by remember { mutableStateOf(languageManager.isAutoSyncEnabled(context)) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Language Settings") },
                backgroundColor = colors.primaryVariant,
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        backgroundColor = colors.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Sync with system language",
                    style = MaterialTheme.typography.body1
                )
                Switch(
                    checked = syncWithSystem,
                    onCheckedChange = {
                        syncWithSystem = it
                        languageManager.saveAutoSyncSetting(context, it)
                        if (it) {
                            languageManager.setLocale(context, Locale.getDefault())
                        }
                    },
                    colors = SwitchDefaults.colors(checkedThumbColor = colors.secondary)
                )
            }
            if (!syncWithSystem) {
                Column {
                    val languages = listOf("English", "中文 (Chinese)", "Français (French)", "Español (Spanish)", "Русский (Russian)", "עברית (Hebrew)")
                    languages.forEach { language ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedLanguage == language,
                                onClick = {
                                    selectedLanguage = language
                                    val newLocale = when (language) {
                                        "English" -> Locale.ENGLISH
                                        "中文 (Chinese)" -> Locale.SIMPLIFIED_CHINESE
                                        "Français (French)" -> Locale.FRENCH
                                        "Español (Spanish)" -> Locale("es")
                                        "Русский (Russian)" -> Locale("ru")
                                        "עברית (Hebrew)" -> Locale("he", "IL")
                                        else -> Locale.ENGLISH
                                    }
                                    languageManager.setLocale(context, newLocale)
                                }
                            )
                            Text(text = language, style = MaterialTheme.typography.body1)
                        }
                    }
                }
            }
        }
    }
}
