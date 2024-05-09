package com.example.cs501finalproject.ui
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cs501finalproject.R
import com.example.cs501finalproject.util.LanguageManager
import com.example.cs501finalproject.util.ThemeManager

@Composable
fun NavigationBar(navController: NavController, modifier: Modifier = Modifier, languageManager: LanguageManager) {
    val colors = ThemeManager.getAppThemeColors()
    val currentLocale by rememberUpdatedState(newValue = languageManager.getCurrentLocale())
    LaunchedEffect(currentLocale) { }
    val selectedTab = remember { mutableStateOf(0) }
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(height = 70.dp)
            //round corner setting
            //.clip(shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
            .background(color = colors.primary)
    ) {
        HomeNavigation(
            modifier = Modifier.weight(weight = 0.2f),
            selected = selectedTab.value == 0,
            colorTheme = colors,
            onClick = {
                selectedTab.value = 0
                navController.navigate("home")
            }
        )
        CalendarNavigation(
            modifier = Modifier.weight(weight = 0.2f),
            selected = selectedTab.value == 1,
            colorTheme = colors,
            onClick = {
                selectedTab.value = 1
                navController.navigate("calendar")
            })
        MemoriesNavigation(
            modifier = Modifier.weight(weight = 0.2f),
            selected = selectedTab.value == 2,
            colorTheme = colors,
            onClick = {
                selectedTab.value = 2
                navController.navigate("memories")
            })
        SettingsNavigation(
            modifier = Modifier.weight(weight = 0.2f),
            selected = selectedTab.value == 3,
            colorTheme = colors,
            onClick = {
                selectedTab.value = 3
                navController.navigate("settings")
            })

        AtlasNavigation(
            modifier = Modifier.weight(weight = 0.2f),
            selected = selectedTab.value == 4,
            colorTheme = colors,
            onClick = {
                selectedTab.value = 4
                navController.navigate("atlas")
            })

    }
}

@Composable
fun HomeNavigation(modifier: Modifier = Modifier, selected: Boolean, colorTheme: Colors, onClick: () -> Unit) {
    NavigationItem(modifier, selected, onClick, R.drawable.ic_home, stringResource(R.string.NavigationBar_Home), colorTheme)
}

@Composable
fun CalendarNavigation(modifier: Modifier = Modifier, selected: Boolean, colorTheme: Colors, onClick: () -> Unit) {
    NavigationItem(modifier, selected, onClick, R.drawable.ic_calendar, stringResource(R.string.NavigationBar_Calendar), colorTheme)
}

@Composable
fun MemoriesNavigation(modifier: Modifier = Modifier, selected: Boolean, colorTheme: Colors, onClick: () -> Unit) {
    NavigationItem(modifier, selected, onClick, R.drawable.ic_memories, stringResource(R.string.NavigationBar_Memories), colorTheme)
}

@Composable
fun SettingsNavigation(modifier: Modifier = Modifier, selected: Boolean, colorTheme: Colors, onClick: () -> Unit) {
    NavigationItem(modifier, selected, onClick, R.drawable.ic_settings, stringResource(R.string.NavigationBar_Settings), colorTheme)
}


@Composable
fun AtlasNavigation(modifier: Modifier = Modifier, selected: Boolean, colorTheme: Colors, onClick: () -> Unit) {
    NavigationItem(modifier, selected, onClick, R.drawable.ic_settings, stringResource(R.string.NavigationBar_Settings), colorTheme)
}
@Composable
fun NavigationItem(modifier: Modifier, isSelected: Boolean, onClick: () -> Unit, iconId: Int, title: String, colorTheme: Colors) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(if (isSelected) colorTheme.primaryVariant else Color.Transparent)
            .padding(top = 6.dp, bottom = 8.dp )
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .requiredWidth(32.dp)
                .clip(shape = RoundedCornerShape(16.dp))
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .requiredWidth(64.dp)
                    .requiredHeight(32.dp)
                    .padding(horizontal = 20.dp, vertical = 4.dp)
            ) {
                Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = "$title Icon",
                    tint = Color(0xff49454f)
                )
            }
        }
        Text(
            text = title,
            color = if (isSelected) Color.Black else Color(0xff49454f),
            fontSize = if (isSelected) 17.sp else 15.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            lineHeight = 1.33.em,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

//@Preview(widthDp = 360, heightDp = 70)
//@Composable
//private fun NavigationBarPreview() {
//    val navController = rememberNavController()
//    NavigationBar(navController, Modifier)
//}
