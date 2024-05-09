package com.example.cs501finalproject.ui

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
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
import com.example.cs501finalproject.HomeBlogListViewModel
import com.example.cs501finalproject.MemoryBlogListDayAgoViewModel
import com.example.cs501finalproject.MemoryBlogListMonthAgoViewModel
import com.example.cs501finalproject.MemoryBlogListWeekAgoViewModel
import com.example.cs501finalproject.MemoryBlogListYearAgoViewModel
import com.example.cs501finalproject.R
import com.example.cs501finalproject.util.ThemeManager
import java.io.File
import java.util.UUID


@Composable
fun MemoriesPage(navController: NavController){
    val colors = ThemeManager.getAppThemeColors()

    Column(
        modifier = Modifier.fillMaxSize().background(colors.background)
    ) {
        MemoriesPictureCarousel(modifier = Modifier.weight(2.7f), colors = colors)
        Banner("A day Ago", colors = colors)
        MemoriesListCarouselDay(navController, modifier = Modifier.weight(1f), colors = colors)
        Banner("A Week Ago", colors = colors)
        MemoriesListCarouselWeek(navController, modifier = Modifier.weight(1f), colors = colors)
        Banner("A Month Ago", colors = colors)
        MemoriesListCarouselMonth(navController, modifier = Modifier.weight(1f), colors = colors)
        Banner("A Year Ago", colors = colors)
        MemoriesListCarouselYear(navController, modifier = Modifier.weight(1f), colors = colors)
    }
}

@Composable
fun Banner(title: String, colors: Colors){
    Box(
        Modifier
            .fillMaxWidth()
            .height(20.dp)
            .background(color = colors.primaryVariant),
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
fun MemoriesPictureCarousel(modifier: Modifier = Modifier, colors: Colors) {
    val memoryBlogListDayAgoViewModel = MemoryBlogListDayAgoViewModel()
    val memoryBlogListWeekAgoViewModel = MemoryBlogListWeekAgoViewModel()
    val memoryBlogListMonthAgoViewModel = MemoryBlogListMonthAgoViewModel()
    val memoryBlogListYearAgoViewModel = MemoryBlogListYearAgoViewModel()
    val dayAgoBlogs = memoryBlogListDayAgoViewModel.blogs.collectAsState(initial = emptyList()).value
    val weekAgoBlogs = memoryBlogListWeekAgoViewModel.blogs.collectAsState(initial = emptyList()).value
    val monthAgoBlogs = memoryBlogListMonthAgoViewModel.blogs.collectAsState(initial = emptyList()).value
    val yearAgoBlogs = memoryBlogListYearAgoViewModel.blogs.collectAsState(initial = emptyList()).value

    val blogs = memoryBlogListDayAgoViewModel.blogs.collectAsState(initial = emptyList())
    val combinedBlogs = remember { mutableStateOf(listOf<Blog>()) }
    LaunchedEffect(dayAgoBlogs, weekAgoBlogs, monthAgoBlogs, yearAgoBlogs) {
        combinedBlogs.value = dayAgoBlogs + weekAgoBlogs + monthAgoBlogs + yearAgoBlogs
    }
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxHeight().background(colors.background)
    ) {
        items(combinedBlogs.value) { item ->
            if (item.photoFileName != ""){
                MemoryPictureItem(item, Modifier.padding(vertical = 8.dp), colors = colors)
            }
        }
    }
}

@Composable
fun MemoryPictureItem(item: Blog, modifier: Modifier, colors: Colors) {
    Card(
        modifier = Modifier.width(230.dp),
        elevation = 4.dp
    ) {
        Column (modifier = Modifier.background(colors.background)) {
            Text(
                text = item.date.toString(),
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .padding(4.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = item.location,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .padding(4.dp),
                textAlign = TextAlign.Center
            )
            item?.photoFileName?.let { fileName ->
                val imageFile = File(fileName)
                if (imageFile.exists()) {
                    val bitmap = BitmapFactory.decodeFile(fileName).asImageBitmap()
                    Image(
                        bitmap = bitmap,
                        contentDescription = "Selected Image",
                        modifier = Modifier.fillMaxWidth()
                            .height(180.dp)
                    )
                } else {
                    Log.d("image File", "non exist")
                }
            }
        }
    }
}


@Composable
fun MemoriesListCarouselDay(navController: NavController, modifier: Modifier, colors: Colors) {
    val blogListViewModel = MemoryBlogListDayAgoViewModel()
    val blogs = blogListViewModel.blogs.collectAsState(initial = emptyList())

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.background(colors.background)
    ) {
        items(blogs.value) { blog ->
            CalendarBlogListItem(blog){
                navController.navigate("blog/${blog.id}")
            }
            Divider(modifier = Modifier.fillMaxWidth(), alpha = 0.1f)
        }
    }
}
@Composable
fun MemoriesListCarouselWeek(navController: NavController, modifier: Modifier, colors: Colors) {
    val blogListViewModel = MemoryBlogListWeekAgoViewModel()
    val blogs = blogListViewModel.blogs.collectAsState(initial = emptyList())

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.background(colors.background)
    ) {
        items(blogs.value) { blog ->
            CalendarBlogListItem(blog){
                navController.navigate("blog/${blog.id}")
            }
            Divider(modifier = Modifier.fillMaxWidth(), alpha = 0.1f)
        }
    }
}
@Composable
fun MemoriesListCarouselMonth(navController: NavController, modifier: Modifier, colors: Colors) {
    val blogListViewModel = MemoryBlogListMonthAgoViewModel()
    val blogs = blogListViewModel.blogs.collectAsState(initial = emptyList())

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.background(colors.background)
    ) {
        items(blogs.value) { blog ->
            CalendarBlogListItem(blog){
                navController.navigate("blog/${blog.id}")
            }
            Divider(modifier = Modifier.fillMaxWidth(), alpha = 0.1f)
        }
    }
}
@Composable
fun MemoriesListCarouselYear(navController: NavController, modifier: Modifier, colors: Colors) {
    val blogListViewModel = MemoryBlogListYearAgoViewModel()
    val blogs = blogListViewModel.blogs.collectAsState(initial = emptyList())

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.background(colors.background)
    ) {
        items(blogs.value) { blog ->
            CalendarBlogListItem(blog){
                navController.navigate("blog/${blog.id}")
            }
            Divider(modifier = Modifier.fillMaxWidth(), alpha = 0.1f)
        }
    }
}


@Preview
@Composable
fun PreviewMemories() {
    val navController = rememberNavController()
    MemoriesPage(navController)
}