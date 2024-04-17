package com.example.cs501finalproject.ui
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.cs501finalproject.R


//@Composable
//fun NavigationBar(modifier: Modifier = Modifier) {
//    Row(
//        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
//        modifier = modifier
//            .fillMaxWidth()
//            .requiredHeight(height = 70.dp)
//            .clip(shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
//            .background(color = Color(0xfff3edf7))
//    ) {
//        HomeNavigation(modifier = Modifier.weight(weight = 0.25f))
//        CalendarNavigation(modifier = Modifier.weight(weight = 0.25f))
//        MemoriesNavigation(modifier = Modifier.weight(weight = 0.25f))
//        SettingsNavigation(modifier = Modifier.weight(weight = 0.25f))
//    }
//}
//
//@Composable
//fun HomeNavigation(modifier: Modifier = Modifier) {
//    Column(
//        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(top = 10.dp,
//                bottom = 8.dp)
//    ) {
//        Row(
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier
//                .requiredWidth(width = 32.dp)
//                .clip(shape = RoundedCornerShape(16.dp))
//        ) {
//            Row(
//                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier
//                    .requiredWidth(width = 64.dp)
//                    .requiredHeight(height = 32.dp)
//                    .padding(horizontal = 20.dp,
//                        vertical = 4.dp)
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_home),
//                    contentDescription = "Home Icon",
//                    tint = Color(0xff49454f))
//            }
//        }
//        Text(
//            text = "Home",
//            color = Color(0xff49454f),
//            textAlign = TextAlign.Center,
//            lineHeight = 1.33.em,
//            modifier = Modifier
//                .fillMaxWidth())
//    }
//}
//
//@Composable
//fun CalendarNavigation(modifier: Modifier = Modifier) {
//    Column(
//        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(top = 10.dp,
//                bottom = 8.dp)
//    ) {
//        Row(
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier
//                .requiredWidth(width = 32.dp)
//                .clip(shape = RoundedCornerShape(16.dp))
//        ) {
//            Row(
//                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier
//                    .requiredWidth(width = 64.dp)
//                    .requiredHeight(height = 32.dp)
//                    .padding(horizontal = 20.dp,
//                        vertical = 4.dp)
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_calendar),
//                    contentDescription = "Calendar Icon",
//                    tint = Color(0xff49454f))
//            }
//        }
//        Text(
//            text = "Calendar",
//            color = Color(0xff49454f),
//            textAlign = TextAlign.Center,
//            lineHeight = 1.33.em,
//            modifier = Modifier
//                .fillMaxWidth())
//    }
//}
//
//@Composable
//fun MemoriesNavigation(modifier: Modifier = Modifier) {
//    Column(
//        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(top = 10.dp,
//                bottom = 8.dp)
//    ) {
//        Row(
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier
//                .requiredWidth(width = 32.dp)
//                .clip(shape = RoundedCornerShape(16.dp))
//        ) {
//            Row(
//                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier
//                    .requiredWidth(width = 64.dp)
//                    .requiredHeight(height = 32.dp)
//                    .padding(horizontal = 20.dp,
//                        vertical = 4.dp)
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_memories),
//                    contentDescription = "Memories Icon",
//                    tint = Color(0xff49454f))
//            }
//        }
//        Text(
//            text = "Memories",
//            color = Color(0xff49454f),
//            textAlign = TextAlign.Center,
//            lineHeight = 1.33.em,
//            modifier = Modifier
//                .fillMaxWidth())
//    }
//}
//
//@Composable
//fun SettingsNavigation(modifier: Modifier = Modifier) {
//    Column(
//        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(top = 10.dp,
//                bottom = 8.dp)
//    ) {
//        Row(
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier
//                .requiredWidth(width = 32.dp)
//                .clip(shape = RoundedCornerShape(16.dp))
////                .background(color = Color(0xffe8def8))
//        ) {
//            Row(
//                horizontalArrangement = Arrangement.Center,
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier
//                    .requiredWidth(width = 64.dp)
//                    .requiredHeight(height = 32.dp)
//                    .padding(horizontal = 20.dp,
//                        vertical = 4.dp)
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_settings),
//                    contentDescription = "Settings",
//                    tint = Color(0xff1d192b))
//            }
//        }
//        Text(
//            text = "Settings",
//            color = Color(0xff1d1b20),
//            textAlign = TextAlign.Center,
//            lineHeight = 1.33.em,
//            style = TextStyle(
//                fontSize = 12.sp,
//                letterSpacing = 0.5.sp),
//            modifier = Modifier
//                .fillMaxWidth())
//    }
//}





@Composable
fun NavigationBar(modifier: Modifier = Modifier) {
    val selectedTab = remember { mutableStateOf(0) }
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(height = 70.dp)
            .clip(shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
            .background(color = Color(0xfff3edf7))
    ) {
        HomeNavigation(
            modifier = Modifier.weight(weight = 0.25f),
            selected = selectedTab.value == 0,
            onClick = { selectedTab.value = 0 })
        CalendarNavigation(
            modifier = Modifier.weight(weight = 0.25f),
            selected = selectedTab.value == 1,
            onClick = { selectedTab.value = 1 })
        MemoriesNavigation(
            modifier = Modifier.weight(weight = 0.25f),
            selected = selectedTab.value == 2,
            onClick = { selectedTab.value = 2 })
        SettingsNavigation(
            modifier = Modifier.weight(weight = 0.25f),
            selected = selectedTab.value == 3,
            onClick = { selectedTab.value = 3 })
    }
}

@Composable
fun HomeNavigation(modifier: Modifier = Modifier, selected: Boolean, onClick: () -> Unit) {
    NavigationItem(modifier, selected, onClick, R.drawable.ic_home, "Home")
}

@Composable
fun CalendarNavigation(modifier: Modifier = Modifier, selected: Boolean, onClick: () -> Unit) {
    NavigationItem(modifier, selected, onClick, R.drawable.ic_calendar, "Calendar")
}

@Composable
fun MemoriesNavigation(modifier: Modifier = Modifier, selected: Boolean, onClick: () -> Unit) {
    NavigationItem(modifier, selected, onClick, R.drawable.ic_memories, "Memories")
}

@Composable
fun SettingsNavigation(modifier: Modifier = Modifier, selected: Boolean, onClick: () -> Unit) {
    NavigationItem(modifier, selected, onClick, R.drawable.ic_settings, "Settings")
}

@Composable
fun NavigationItem(modifier: Modifier, isSelected: Boolean, onClick: () -> Unit, iconId: Int, title: String) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(if (isSelected) Color(0xFFE8DEF8) else Color.Transparent)
            .padding(top = 6.dp, bottom = 6.dp)
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

@Preview(widthDp = 360, heightDp = 70)
@Composable
private fun NavigationBarPreview() {
    NavigationBar(Modifier)
}
