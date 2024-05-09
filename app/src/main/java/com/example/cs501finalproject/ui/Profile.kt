package com.example.cs501finalproject.ui
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cs501finalproject.R
import com.example.cs501finalproject.util.ThemeManager

@Composable
fun Profile(navController: NavController) {
    val colors = ThemeManager.getAppThemeColors()

    Column(){
        TopSection(colors)
//        AccountSection(navController, colors)
        GeneralSection(navController, colors)
        AboutSection(navController, colors)
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(colors.background)
        )
    }

}



@Composable
fun TopSection(colors: Colors) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(130.dp)
            .background(
                brush = Brush.linearGradient(
                    0f to colors.primary,
                    1f to colors.secondaryVariant
                )
            )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(id = R.string.Settings_Settings),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.Home_welcome_back),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                )
            )
        }
    }
}

@Composable
fun AccountSection(navController: NavController, colors: Colors) {
    Column {
        SectionHeader(title = R.string.Settings_Account, colors = colors)

        // Profile
        SectionItem(navController, colors, R.string.Settings_Profile)

        // Divider Line
        Divider(color = Color.Gray.copy(alpha = 0.4f), thickness = 1.dp)

        // Instagram Binding
        SectionItem(navController, colors, R.string.Settings_Instagram_Binding)
    }
}

@Composable
fun GeneralSection(navController: NavController, colors: Colors) {
    Surface {
        Column {
            // General Section Header
            SectionHeader(title = R.string.Settings_General, colors = colors)

            // Language
            SectionItem(navController, colors, R.string.Settings_Language, "settingsLanguage")

            // Divider Line
            Divider(color = Color.Gray.copy(alpha = 0.4f), thickness = 1.dp)

            // Theme
            SectionItem(navController, colors, R.string.Settings_Theme, "settingsTheme")

            // Divider Line
            Divider(color = Color.Gray.copy(alpha = 0.4f), thickness = 1.dp)

            // Cloud Sync
            //SectionItem(navController, colors, R.string.Settings_Cloud_Sync)

            // Divider Line
            Divider(color = Color.Gray.copy(alpha = 0.4f), thickness = 1.dp)

            // Notification
            SectionItem(navController, colors, R.string.Settings_Notification, "settingsNotifications")
        }
    }
}

@Composable
fun AboutSection(navController: NavController, colors: Colors) {
    Surface {
        Column {
            // About Section Header
            SectionHeader(title = R.string.Settings_About, colors = colors)

            // About Info
            SectionItem(navController, colors, R.string.Settings_About, "settingsAbout")

            // Divider Line
            Divider(color = Color.Gray.copy(alpha = 0.4f), thickness = 1.dp)
        }
    }
}

@Composable
fun SectionHeader(title: Int, colors: Colors) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(20.dp)
            .background(color = colors.primaryVariant),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = title),
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight(500),
                textAlign = TextAlign.Center,
                letterSpacing = 0.1.sp,
            )
        )
    }
}

@Composable
fun SectionItem(navController: NavController, colors: Colors, labelResourceId: Int, navigationRoute: String? = null) {
    Column {
        Box(
            Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(color = colors.background)
                .clickable(enabled = navigationRoute != null) {
                    navigationRoute?.let { navController.navigate(it) }
                }
        ) {
            Text(
                text = stringResource(id = labelResourceId),
                style = TextStyle(
                    fontSize = 22.sp,
                    lineHeight = 28.sp,
                    fontWeight = FontWeight(400)
                ),
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 15.dp)
            )
        }
    }
}


@Preview
@Composable
fun PreviewProfileSettingsPage() {
    Profile(rememberNavController())
}