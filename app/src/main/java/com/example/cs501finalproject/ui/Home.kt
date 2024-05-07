package com.example.cs501finalproject.ui

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cs501finalproject.BlogDetailViewModel
import com.example.cs501finalproject.BlogDetailViewModelFactory
import com.example.cs501finalproject.CalenderBlogListViewModel
import com.example.cs501finalproject.DateViewModel
import com.example.cs501finalproject.HomeBlogListViewModel
import com.example.cs501finalproject.R
import com.example.cs501finalproject.util.ThemeManager
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID


@Composable
fun HomePage(navController: NavController, dateViewModel: DateViewModel) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBanner()
        SearchFilter(onDateRangeSelected =
            { fromDate, toDate -> /* Your logic here */ },
            context = LocalContext.current, dateViewModel)
        HomePictureCarousel(modifier = Modifier.weight(2f))
        HomeListCarousel(navController, modifier = Modifier.weight(3f), dateViewModel)
    }
}

fun getSampleDataHome(): List<EventItem> {
    val numericString = "1"
    val uuid = UUID.nameUUIDFromBytes(numericString.toByteArray())
    return listOf(
        EventItem(1, "2024/04/17", "New Zealand", "NZ holiday",
            R.drawable.ic_dashboard_black_24dp, "\u2708"),
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
fun SearchFilter(onDateRangeSelected: (fromDate: String, toDate: String) -> Unit, context: Context, dateViewModel: DateViewModel) {
    val startDate = dateViewModel.startDate.collectAsState(initial = LocalDate.now().withDayOfYear(1))
    val endDate =  dateViewModel.startDate.collectAsState(initial = LocalDate.now())

    val fromDateState = remember { mutableStateOf( startDate.value.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")) )}
    val toDateState = remember {
        val currentDate = endDate.value.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        mutableStateOf(currentDate)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text("From", modifier = Modifier.padding(end = 8.dp), color = Color.Black)
        Button(
            onClick = { showDatePickerDialog(true, fromDateState.value, toDateState.value, onDateRangeSelected, context, fromDateState, toDateState, dateViewModel) },
            modifier = Modifier
                .width(120.dp)
                .height(46.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
        ) {
            Text(fromDateState.value, color = Color.Black)
        }
        Text("To", modifier = Modifier.padding(end = 8.dp), color = Color.Black)
        Button(
            onClick = { showDatePickerDialog(false, fromDateState.value, toDateState.value, onDateRangeSelected, context, fromDateState, toDateState, dateViewModel) },
            modifier = Modifier
                .width(120.dp)
                .height(46.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
        ) {
            Text(toDateState.value, color = Color.Black)
        }
    }
}


fun showDatePickerDialog(isFromDate: Boolean, fromDate: String, toDate: String, onDateRangeSelected: (fromDate: String, toDate: String) -> Unit, context: Context, fromDateState: MutableState<String>, toDateState: MutableState<String>, dateViewModel: DateViewModel) {
    val selectedDate = if (isFromDate) fromDate else toDate
    val initialYear = selectedDate.substring(0, 4).toInt()
    val initialMonth = selectedDate.substring(5, 7).toInt() - 1
    val initialDay = selectedDate.substring(8, 10).toInt()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, monthOfYear, dayOfMonth ->
            val formattedDate = String.format("%04d/%02d/%02d", year, monthOfYear + 1, dayOfMonth)
            if (isFromDate) {
                fromDateState.value = formattedDate
            } else {
                toDateState.value = formattedDate
            }
            onDateRangeSelected(fromDateState.value, toDateState.value)
            // log the newly selected dates
            Log.d("SelectedDates", "From: ${fromDateState.value}, To: ${toDateState.value}")
            val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
            dateViewModel.updateStartDate(LocalDate.parse(fromDateState.value, formatter))
            dateViewModel.updateEndDate(LocalDate.parse(toDateState.value, formatter))
        },
        initialYear,
        initialMonth,
        initialDay
    )
    datePickerDialog.show()

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
fun HomeListCarousel(navController: NavController, modifier: Modifier, dateViewModel: DateViewModel) {
    val startDate = dateViewModel.startDate.collectAsState(initial = LocalDate.now().withDayOfYear(1))
    val endDate =  dateViewModel.startDate.collectAsState(initial = LocalDate.now())
    val homeBlogListViewModel = HomeBlogListViewModel(startDate.value, endDate.value)
    val blogs = homeBlogListViewModel.blogs.collectAsState(initial = emptyList())

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(blogs.value) { blog ->
            CalendarBlogListItem(blog){
                navController.navigate("blog/${blog.id}")
            }
        }
    }
}



@Preview
@Composable
fun PreviewHomePage() {
    HomePage(rememberNavController(), DateViewModel())
}
