package com.example.cs501finalproject.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cs501finalproject.R

@Composable
fun SettingsAboutPage(navController: NavController) {
    // Fetching current theme colors
    val colors = getAppThemeColors(currThemeState)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.Settings_About)) },
                backgroundColor = colors.secondary,
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        // Setting background color
        backgroundColor = colors.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.Settings_About_Version),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Divider()
            Text(
                text = stringResource(R.string.Settings_About_Description),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
                textAlign = TextAlign.Justify,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsAboutPage() {
    val navController = rememberNavController()
    SettingsAboutPage(navController)
}

