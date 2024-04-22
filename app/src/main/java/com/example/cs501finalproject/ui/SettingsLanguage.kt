package com.example.cs501finalproject.ui

import android.content.Context
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cs501finalproject.R
import com.example.cs501finalproject.util.LanguageManager
//import com.example.cs501finalproject.util.ResourceManager
import java.util.Locale

@Composable
fun SettingsLanguagePage(navController: NavController, languageManager: LanguageManager) {
    val context = LocalContext.current
    val currentLocale = languageManager.getCurrentLocale()
    var selectedLanguage by remember { mutableStateOf(
        when (currentLocale) {
            Locale.ENGLISH -> "English"
            Locale.SIMPLIFIED_CHINESE -> "中文 (Chinese)"
            else -> "English"
        }
    )}
    val languages = listOf(
        "English",
        "中文 (Chinese)",
//        "Français (France)",
//        "Español (Spanish)",
//        "Русский (Russian)",
//        " (Hebrew)עבריתִִ"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.Settings_SelectLanguage)) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Button(
                        onClick = {
                            val newLocale = when (selectedLanguage) {
                                "English" -> Locale.ENGLISH
                                "中文 (Chinese)" -> Locale.SIMPLIFIED_CHINESE
                                else -> Locale.getDefault()
                            }
                            languageManager.setLocale(context, newLocale)
                            //navController.navigateUp()

                        }
                    ) {
                        Text(stringResource(R.string.Settings_Confirm))
                    }
                }
            )
        }
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