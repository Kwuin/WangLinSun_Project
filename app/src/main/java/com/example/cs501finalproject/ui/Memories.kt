package com.example.cs501finalproject.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController
import com.example.cs501finalproject.Blog
import com.example.cs501finalproject.R


data class EventItem(
    val blogId: Int?,
    val date: String,
    val location: String,
    val title: String,
    val imageId: Int,
    val emoji: String
)

fun getSampleData(): List<EventItem> {
    return listOf(
        EventItem(null, "Yesterday", "New Zealand", "NZ holiday", R.drawable.blog_example, "\uD83E\uDD29"),
        EventItem(null, "1 Week Ago", "New Zealand", "NZ holiday", R.drawable.blog_boulders, "\uD83E\uDD29"),
        EventItem(null, "1 Week Ago", "New Zealand", "NZ holiday", R.drawable.blog_example, "\uD83D\uDCBB"),
        EventItem(null, "1 Month Ago", "Australia", "Spring Break", R.drawable.blog_nature_window, "\u2708"),
        EventItem(null, "1 Year Ago", "Boston", "Rainy", R.drawable.blog_boulders, "\u2614"),
        EventItem(null, "1 Year Ago", "Boston", "Rainy", R.drawable.blog_boulders, "\u2614"),
        EventItem(null, "1 Year Ago", "Boston", "Rainy", R.drawable.blog_boulders, "\u2614")
    )
}
fun getSampleDataDay(): List<EventItem> {
    return listOf(
        EventItem(null, "2024/04/17", "New Zealand", "NZ holiday", R.drawable.blog_example, "\uD83E\uDD29")
    )
}
fun getSampleDataWeek(): List<EventItem> {
    return listOf(
        EventItem(null, "2024/04/11", "New Zealand", "NZ holiday", R.drawable.blog_boulders, "\uD83E\uDD29"),
        EventItem(null, "2024/04/11", "New Zealand", "NZ holiday", R.drawable.blog_example, "\uD83D\uDCBB")
    )
}
fun getSampleDataMonth(): List<EventItem> {
    return listOf(
        EventItem(null, "2024/03/18", "Australia", "Spring Break", R.drawable.blog_nature_window, "\u2708"),
    )
}
fun getSampleDataYear(): List<EventItem> {
    return listOf(
        EventItem(null, "2023/04/18", "Boston", "Rainy", R.drawable.blog_boulders, "\u2614"),
        EventItem(null, "2023/04/18", "Boston", "Rainy", R.drawable.blog_boulders, "\u2614"),
        EventItem(null, "2023/04/18", "Boston", "Rainy", R.drawable.blog_boulders, "\u2614")
    )
}


@Composable
fun MemoriesPage(navController: NavController){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        MemoriesPictureCarousel(modifier = Modifier.weight(3f))
        Banner("A day Ago")
        MemoriesListCarouselDay(modifier = Modifier.weight(1f))
        Banner("A Week Ago")
        MemoriesListCarouselWeek(modifier = Modifier.weight(1f))
        Banner("A Month Ago")
        MemoriesListCarouselMonth(modifier = Modifier.weight(1f))
        Banner("A Year Ago")
        MemoriesListCarouselYear(modifier = Modifier.weight(1f))
    }
}

@Composable
fun Banner(title: String){
    Box(
        Modifier
            .fillMaxWidth()
            .height(20.dp)
            .background(color = Color(0xFFD4CADC)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight(500),
                color = Color(0xFF000000),
                textAlign = TextAlign.Center,
                letterSpacing = 0.1.sp,
            )
        )
    }
}

@Composable
fun MemoriesPictureCarousel(modifier: Modifier = Modifier) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxHeight()
    ) {
        items(getSampleData()) { item ->
            MemoryPictureItem(item, Modifier.padding(vertical = 8.dp))
        }
    }
}

@Composable
fun MemoryPictureItem(item: EventItem, modifier: Modifier) {
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
fun MemoriesListCarouselDay(modifier: Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(getSampleDataDay()) { item ->
            MemoryListItem(item)
        }
    }
}
@Composable
fun MemoriesListCarouselWeek(modifier: Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(getSampleDataWeek()) { item ->
            MemoryListItem(item)
        }
    }
}
@Composable
fun MemoriesListCarouselMonth(modifier: Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(getSampleDataMonth()) { item ->
            MemoryListItem(item)
        }
    }
}
@Composable
fun MemoriesListCarouselYear(modifier: Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(getSampleDataYear()) { item ->
            MemoryListItem(item)
        }
    }
}

@Composable
fun MemoryListItem(item: EventItem) {
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

//@Composable
//fun Memorylist(title: String, blogs: List<EventItem>){
//    Column(){
//        Box(
//            Modifier
//                .fillMaxWidth()
//                .height(20.dp)
//                .background(color = Color(0xFFD4CADC))
//            ,
//            contentAlignment = Alignment.Center
//        ) {
//            Text(
//                text = title,
//
//                style = TextStyle(
//                    fontSize = 14.sp,
//                    lineHeight = 20.sp,
//                    fontWeight = FontWeight(500),
//                    color = Color(0xFF000000),
//                    textAlign = TextAlign.Center,
//                    letterSpacing = 0.1.sp,
//                )
//            )
//        }
//        LazyColumn(){
//            items(blogs){blog ->
//                MemoryListItem(item = blog)
//            }
//        }
//
//    }
//}

@Preview
@Composable
fun PreviewMemories() {
    val navController = rememberNavController()
    MemoriesPage(navController)
}