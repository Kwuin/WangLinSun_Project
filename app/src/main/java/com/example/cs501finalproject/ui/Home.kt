package com.example.cs501finalproject.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cs501finalproject.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID


@Composable
fun HomePage(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBanner()
        SearchFilter()
        HomePictureCarousel(modifier = Modifier.weight(2f))
        HomeListCarousel(modifier = Modifier.weight(3f))
    }
}

fun getSampleDataHome(): List<EventItem> {
    val numericString = "1"
    val uuid = UUID.nameUUIDFromBytes(numericString.toByteArray())
    return listOf(
        EventItem(1, "2024/04/17", "New Zealand", "NZ holiday", R.drawable.blog_boulders, "\u2708"),
        EventItem(2, "2024/04/16", "Boston", "Busy Day", R.drawable.blog_example, "\uD83D\uDCBB"),
        EventItem(null, "2024/04/13", "Australia", "Spring Break", R.drawable.blog_nature_window, "\uD83D\uDE0A"),
        EventItem(null, "2024/04/10", "New Zealand", "NZ holiday", R.drawable.blog_boulders, "\uD83D\uDE0A"),
        EventItem(null, "2024/04/01", "Boston", "Rainy", R.drawable.blog_example, "\u2614"),
        EventItem(null, "2024/04/01", "Boston", "Boring", R.drawable.blog_blank, "\uD83D\uDE22"),
        EventItem(null, "2024/03/25", "Boston", "Default Title", R.drawable.blog_blank, "\uD83D\uDE0A"),
        EventItem(null, "2024/03/23", "Boston", "Happy Birthday", R.drawable.blog_blank, "\uD83D\uDE0A"),
        EventItem(null, "2024/03/21", "Boston", "Busy Weekend", R.drawable.blog_blank, "\uD83D\uDCBB"),
        EventItem(null, "2024/03/15", "Boston", "WOW", R.drawable.blog_blank, "\uD83E\uDD29"),
        EventItem(null, "2024/03/02", "Boston", "Cold", R.drawable.blog_blank, "\uD83E\uDD76"),
        EventItem(null, "2024/02/20", "Boston", "Trip to LA", R.drawable.blog_blank, "\uD83D\uDE0A"),
    )
}

@Composable
fun TopBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(color = Color(0xFFD4CADC)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Welcome back, Tom!",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    stringResource(R.string.Home_no_journal_today_create_one),
                    color = Color.Black,
                    fontSize = 15.sp
                )
            }
            Text(
                LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, MMMM dd")),
                color = Color.Black,
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun SearchFilter() {
    var fromDate by remember { mutableStateOf("2024/01/01") }
    var toDate by remember { mutableStateOf("2024/04/18") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(stringResource(R.string.Home_From), modifier = Modifier.padding(end = 8.dp), color = Color.Black)
        OutlinedTextField(
            value = fromDate,
            onValueChange = { fromDate = it },
            placeholder = { Text(text = stringResource(R.string.Home_Date), color = Color.Gray)},
            singleLine = true,
            modifier = Modifier
                .width(120.dp)
                .height(46.dp)
                .border(1.dp, Color.Magenta, shape = RoundedCornerShape(4.dp)),
            textStyle = TextStyle(color = Color.Black),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                focusedBorderColor = Color.Magenta,
                unfocusedBorderColor = Color(0xFFD4CADC),
                cursorColor = Color.Blue
            )
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(stringResource(R.string.Home_To), modifier = Modifier.padding(end = 8.dp), color = Color.Black)
        OutlinedTextField(
            value = toDate,
            onValueChange = { toDate = it },
            placeholder = { Text(text = stringResource(R.string.Home_Date), color = Color.Gray)},
            singleLine = true,
            modifier = Modifier
                .width(120.dp)
                .height(46.dp)
                .border(1.dp, Color.Magenta, shape = RoundedCornerShape(4.dp)),
            textStyle = TextStyle(color = Color.Black),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                focusedBorderColor = Color.Magenta,
                unfocusedBorderColor = Color(0xFFD4CADC),
                cursorColor = Color.Blue
            )
        )
    }
}

@Composable
fun HomePictureCarousel(modifier: Modifier = Modifier) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxHeight()
    ) {
        items(getSampleDataHome()) { item ->
            MemoryPictureItem(item, Modifier.padding(vertical = 8.dp))
        }
    }
}

@Composable
fun HomePictureItem(item: EventItem, modifier: Modifier) {
    Card(
        modifier = Modifier.width(200.dp),
        elevation = 4.dp
    ) {
        Column {
            Image(
                painter = painterResource(id = item.imageId),
                contentDescription = "Event Image",
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
            Text(
                text = item.date,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = item.location,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun HomeListCarousel(modifier: Modifier) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(getSampleDataHome()) { item ->
            HomeListItem(item)
        }
    }
}


@Composable
fun HomeListItem(item: EventItem) {
//    navigation("blog/$item.id")
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 时间和地点信息区 (左侧 3/10)
        Column(
            modifier = Modifier.weight(3f),
        ) {
            Text(
                text = item.date,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 4.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = item.location,
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
        // 博客标题 (中间 6/10)
        Text(
            text = item.title,
            fontSize = 16.sp,
            modifier = Modifier
                .weight(6f)
                .padding(horizontal = 8.dp),
            textAlign = TextAlign.Start
        )
        // 表情符号 (右侧 1/10)
        Text(
            text = item.emoji,
            fontSize = 24.sp,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun PreviewHomePage() {
    HomePage(rememberNavController())
}
