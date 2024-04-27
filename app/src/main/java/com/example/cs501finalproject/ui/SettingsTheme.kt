package com.example.cs501finalproject.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cs501finalproject.R

@Composable
fun SettingsThemePage(navController: NavController) {
    var selectedThemeState by remember { mutableStateOf(currThemeState) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.Settings_Theme_SelectTheme)) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Button(
                        onClick = {
                            currThemeState = selectedThemeState
//                            navController.navigateUp()
                        }
                    ) {
                        Text("Confirm")
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            val themes = ThemeState.values()
            for (i in themes.indices step 2) {
                Row {
                    themes.slice(i until (i + 2).coerceAtMost(themes.size)).forEach { theme ->
                        ThemeOption(theme, Modifier.weight(1f), selectedThemeState, onThemeSelected = {
                            selectedThemeState = it
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun ThemeOption(themeType: ThemeState, modifier: Modifier, selectedThemeState: ThemeState, onThemeSelected: (ThemeState) -> Unit) {
    val colors = getAppThemeColors(themeType)
    Row(
        modifier = modifier
            .padding(8.dp)
            .clickable(onClick = { onThemeSelected(themeType) })
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(200.dp)
                .background(colors.primary, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = themeType.name,
                style = MaterialTheme.typography.subtitle1
            )
        }
        RadioButton(
            selected = themeType == selectedThemeState,
            onClick = { onThemeSelected(themeType) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsThemePage() {
    val navController = rememberNavController()
    SettingsThemePage(navController)
}
