package com.example.cs501finalproject.ui

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cs501finalproject.DateViewModel
import com.example.cs501finalproject.HomeBlogListViewModel
import com.example.cs501finalproject.R
import com.example.cs501finalproject.util.ThemeManager
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun HomePage(navController: NavController, dateViewModel: DateViewModel) {
    // get startDate and endDate from viewModel
    val startDate = dateViewModel.startDate.collectAsState(initial = LocalDate.now().withDayOfYear(1))
    val endDate =  dateViewModel.endDate.collectAsState(initial = LocalDate.now())

    // UI
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBanner()
        SearchFilter(onDateRangeSelected = { fromDate, toDate -> /* Your logic here */ },
            context = LocalContext.current, dateViewModel, startDate, endDate)
        HomePictureCarousel(modifier = Modifier.weight(2f),startDate, endDate)
        Divider(modifier = Modifier.fillMaxWidth(), alpha = 0.3f)
        HomeListCarousel(navController, modifier = Modifier.weight(3f), startDate, endDate)
    }
}


@Composable
fun TopBanner() {
    val colors = ThemeManager.getAppThemeColors()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(color = colors.primaryVariant),
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
                    "Welcome back!",
                    color = colors.onPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            // display current date
            Text(
                LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, MMMM dd")),
                color = colors.onPrimary,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}


@Composable
fun SearchFilter(onDateRangeSelected: (fromDate: String, toDate: String) -> Unit, context: Context, dateViewModel: DateViewModel, startDate: State<LocalDate>, endDate: State<LocalDate>) {
    val colors = ThemeManager.getAppThemeColors()

    val fromDateState = remember { mutableStateOf( startDate.value.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")) )}
    val toDateState = remember {
        val currentDate = endDate.value.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        mutableStateOf(currentDate)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(vertical = 0.dp)
            .background(color = colors.primary),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text("From", modifier = Modifier.padding(end = 8.dp), fontWeight = FontWeight.Bold, color = colors.onPrimary)
        Button(
            onClick = { showDatePickerDialog(true, fromDateState.value, toDateState.value, onDateRangeSelected, context, fromDateState, toDateState, dateViewModel) },
            modifier = Modifier
                .width(120.dp)
                .height(40.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = colors.primaryVariant)
        ) {
            Text(startDate.value.toString(), color = Color.Black)
        }
        Text("To", modifier = Modifier.padding(end = 8.dp), fontWeight = FontWeight.Bold, color = colors.onPrimary)
        Button(
            onClick = { showDatePickerDialog(false, fromDateState.value, toDateState.value, onDateRangeSelected, context, fromDateState, toDateState, dateViewModel) },
            modifier = Modifier
                .width(120.dp)
                .height(40.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = colors.primaryVariant)
        ) {
            Text(endDate.value.toString(), color = Color.Black)
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
        ThemeManager.getDialogThemeId(),
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
    datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(context, R.color.black));
    datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context, R.color.black));
}


@Composable
fun HomePictureCarousel(modifier: Modifier = Modifier, startDate: State<LocalDate>, endDate: State<LocalDate>) {
    val homeBlogListViewModel = HomeBlogListViewModel(startDate.value, endDate.value)
    val blogs = homeBlogListViewModel.blogs.collectAsState(initial = emptyList())
    val colors = ThemeManager.getAppThemeColors()
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxHeight().background(color = colors.background),
    ) {
        items(blogs.value) { item ->
            if (item.photoFileName != null) {
                MemoryPictureItem(item, Modifier.padding(vertical = 8.dp), colors = colors)
            }
        }
    }
}


@Composable
fun HomeListCarousel(navController: NavController, modifier: Modifier, startDate: State<LocalDate>, endDate: State<LocalDate>) {
    Log.d("HomeListCarousel", "HomeListCarousel is reached with startdate being ${startDate.value} and enddate being ${endDate.value}")
    val homeBlogListViewModel = HomeBlogListViewModel(startDate.value, endDate.value)
    val blogs = homeBlogListViewModel.blogs.collectAsState(initial = emptyList())
    val colors = ThemeManager.getAppThemeColors()
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxHeight().background(color = colors.background),
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
fun Divider(modifier: Modifier = Modifier, alpha: Float) {
    val colors = ThemeManager.getAppThemeColors()
    Row (modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center) {
        Box(
            modifier = modifier
                .width(5.dp)
                .height(1.dp)
                .background(color = colors.onBackground.copy(alpha = alpha))
        )
    }
}


@Preview
@Composable
fun PreviewHomePage() {
    HomePage(rememberNavController(), DateViewModel())
}
