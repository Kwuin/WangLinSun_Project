package com.example.cs506finalproject.ui
import androidx.compose.foundation.background
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

@Composable
fun NavigationBar(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(height = 70.dp)
            .clip(shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
            .background(color = Color(0xfff3edf7))
    ) {
        ActiveFalseStateEnabledLabeltrueBadgeNone(
            modifier = Modifier
                .weight(weight = 0.25f))
        ActiveFalseStateEnabledLabeltrueBadgeNone(
            modifier = Modifier
                .weight(weight = 0.25f))
        ActiveFalseStateEnabledLabeltrueBadgeNone(
            modifier = Modifier
                .weight(weight = 0.25f))
        ActiveTrueStateEnabledLabeltrueBadgeNone(
            modifier = Modifier
                .weight(weight = 0.25f))
    }
}

@Composable
fun ActiveFalseStateEnabledLabeltrueBadgeNone(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 10.dp,
                bottom = 8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .requiredWidth(width = 32.dp)
                .clip(shape = RoundedCornerShape(16.dp))
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .requiredWidth(width = 64.dp)
                    .requiredHeight(height = 32.dp)
                    .padding(horizontal = 20.dp,
                        vertical = 4.dp)
            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.bookmark_border_24px),
//                    contentDescription = "Icon",
//                    tint = Color(0xff49454f))
            }
        }
        Text(
            text = "Memories",
            color = Color(0xff49454f),
            textAlign = TextAlign.Center,
            lineHeight = 1.33.em,
            modifier = Modifier
                .fillMaxWidth())
    }
}

@Composable
fun ActiveTrueStateEnabledLabeltrueBadgeNone(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 10.dp,
                bottom = 8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clip(shape = RoundedCornerShape(16.dp))
                .background(color = Color(0xffe8def8))
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .requiredWidth(width = 64.dp)
                    .requiredHeight(height = 32.dp)
                    .padding(horizontal = 20.dp,
                        vertical = 4.dp)
            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.settings_filled_24px),
//                    contentDescription = "Icon",
//                    tint = Color(0xff1d192b))
            }
        }
        Text(
            text = "Settings",
            color = Color(0xff1d1b20),
            textAlign = TextAlign.Center,
            lineHeight = 1.33.em,
            style = TextStyle(
                fontSize = 12.sp,
                letterSpacing = 0.5.sp),
            modifier = Modifier
                .fillMaxWidth())
    }
}

@Preview(widthDp = 360, heightDp = 70)
@Composable
private fun NavigationBarPreview() {
    NavigationBar(Modifier)
}