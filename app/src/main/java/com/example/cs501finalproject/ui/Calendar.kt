package com.example.cs501finalproject.ui

import androidx.compose.material.*
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cs501finalproject.Blog
import com.example.cs501finalproject.BlogDateVIewModel
import com.example.cs501finalproject.BlogListViewModel
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale
import com.example.cs501finalproject.CalenderBlogListViewModel
import com.example.cs501finalproject.util.ThemeManager
import com.example.cs501finalproject.util.getAppThemeColors
import kotlinx.coroutines.launch
import java.util.UUID
import com.google.android.material.snackbar.Snackbar



sealed class Screen(val route: String) {
    object Calendar : Screen("Calendar")
    object BlogDetail : Screen("BlogDetail")
}



@Composable
fun CalendarPage(navController: NavController){


    val today = LocalDate.now()
    // Get the day of the month as an integer (e.g., 1, 2, 3, ..., 31)
    var dayClicked by remember { mutableStateOf(today.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()))}
    var dateClicked by remember { mutableStateOf("${today.dayOfMonth}") }
    var monthClicked by remember { mutableStateOf(YearMonth.now().month.name) }


    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val blogDateVIewModel = BlogDateVIewModel()


    Surface(modifier = Modifier.fillMaxSize()) {
        Column (Modifier.background(color = Color(0xFFF3EDF7)),
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
        ){

                Text(
                    text = "Date Selected",
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF49454F),
                        letterSpacing = 0.1.sp,
                    ),
                    modifier = Modifier
                        .padding(start = 20.dp, top = 10.dp)
                )
                Text(
                    text = "$dayClicked, $monthClicked $dateClicked",
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = 32.sp,
                        lineHeight = 40.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF49454F),
                        letterSpacing = 0.1.sp,
                    ),
                    modifier = Modifier
                        .padding(start = 20.dp, top = 0.dp)
                )



            Divider(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                color = Color.LightGray,
                thickness = 1.dp
            )
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(26.dp)
                    .background(color = Color(0xFFF3EDF7))
                    .padding(start = 16.dp, top = 4.dp, end = 12.dp, bottom = 4.dp)){
            }


            val currentMonth = remember { YearMonth.now() }
            val startMonth = remember { currentMonth.minusMonths(100) } // Adjust as needed
            val endMonth = remember { currentMonth.plusMonths(100) } // Adjust as needed
            val firstDayOfWeek = remember { firstDayOfWeekFromLocale() } // Available from the library

            var selectedDate by remember { mutableStateOf<LocalDate?>(LocalDate.now()) }
            var selectedMonth by remember {
                mutableStateOf<YearMonth>(YearMonth.now())
            }
            val state = rememberCalendarState(
                startMonth = startMonth,
                endMonth = endMonth,
                firstVisibleMonth = currentMonth,
                firstDayOfWeek = firstDayOfWeek
            )

            HorizontalCalendar(
                state = state,
                dayContent = {  day ->
                    Day(day, blogDateVIewModel, isSelected = selectedDate == day.date) { day ->
                        selectedDate = day.date
                        dayClicked = "${selectedDate?.dayOfWeek?.getDisplayName(TextStyle.FULL, Locale.getDefault())}"
                        monthClicked = day.date.month.name
                        dateClicked = "${selectedDate?.dayOfMonth}"
                    } },
                monthHeader = { month ->
                    val daysOfWeek = month.weekDays.first().map { it.date.dayOfWeek }
                    selectedMonth = month.yearMonth
                    MonthHeader(daysOfWeek = daysOfWeek, month.yearMonth.month.name, month.yearMonth.year)
                }
            )



//    If you need a vertical calendar.
//    VerticalCalendar(
//        state = state,
//        dayContent = { Day(it) }
//    )
            Divider(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                color = Color.LightGray,
                thickness = 3.dp
            )
            Box(Modifier.align(Alignment.CenterHorizontally) // Center-align this item
            ){
                Button(onClick = {
                    if (selectedDate == null){
                        Toast.makeText(context, "You haven't selected a date!", Toast.LENGTH_LONG).show()
                    }else {
                        val newBlog = Blog(
                            title = "My new blog today",
                            date = selectedDate!!,
                            text = "",
                            photoFileName = "",
                            location = "",
                            emoji = ""
                        )
                        val blogListViewModel = CalenderBlogListViewModel(selectedDate!!)
                        coroutineScope.launch {
                            blogListViewModel.addBlog(newBlog)
                            Log.d("Navigation", "new/${newBlog.id}")
                            //val blog = blogListViewModel.getBlog(newBlog.id)
                            navController.navigate("blog/${newBlog.id}")
                        }
                    }
                },
                    Modifier
                        .width(144.dp)
                        .height(38.dp)
                    ,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFD29CEC), // Make button content area transparent
                    ),
                    shape = RoundedCornerShape(100.dp),
                    ){
                    Text(
                        text = "Add New Journal",
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 10.sp,
                            lineHeight = 40.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF49454F),
                            letterSpacing = 0.1.sp,
                        )
                    )
                }
            }
            Divider(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                color = Color.LightGray,
                thickness = 2.dp
            )
            val calenderBlogListViewModel = selectedDate?.let { CalenderBlogListViewModel(it) }
            val blogs = calenderBlogListViewModel?.blogs?.collectAsState(initial = emptyList())

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(3f)
            ) {
                 if (calenderBlogListViewModel != null) {
                     if (blogs != null) {
                         items(blogs.value) { blog ->
                             CalendarBlogListItem(blog){
                                 navController.navigate("blog/${blog.id}")
                             }
                         }
                     }
                }
            }



        }
    }
}



@Composable
fun Calendar() {
}

@Composable
fun MonthHeader(daysOfWeek: List<DayOfWeek>, currentMonth: String, currentYear: Int) {
    Column{
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "$currentMonth $currentYear",
        )
        Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
            )
        }
    }}
}

@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
            )
        }
    }
}
@Composable
fun Day(day: CalendarDay, blogDateVIewModel: BlogDateVIewModel, isSelected: Boolean, onClick: (CalendarDay) -> Unit) {
    val colors = ThemeManager.getAppThemeColors()
    val addressMap  = blogDateVIewModel.addresses.collectAsState(initial = mutableMapOf())
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .aspectRatio(1f)
            .background(color = if (isSelected) colors.primaryVariant else Color.Transparent)
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { onClick(day) }
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(){
            Text(
                text = day.date.dayOfMonth.toString(),
                color = if (day.position == DayPosition.MonthDate) Color.Black else Color.Gray
            )
            Box(
                modifier = Modifier
                    .size(5.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally) // Align horizontally
                    .background(color = if (addressMap.value[day.date] == true)  colors.secondaryVariant else Color.Transparent)
            )
        }
    }
}


@Preview
@Composable
fun Checkcalendar() {
//    GradientBackground()
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .aspectRatio(1f)
            ,
        contentAlignment = Alignment.Center
    ) {
        Column(){
            Text(
                text = "10",
                color = Color.Gray
            )
            Box(
                modifier = Modifier
                    .size(3.dp)
                    .clip(CircleShape)
                    .background(Color.Red)
                    .align(Alignment.CenterHorizontally) // Align horizontally
            )
        }

    }}


@Composable
fun CalendarBlogListItem(item: Blog, onClick: () -> Unit) {

//    navigation("blog/$item.id")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(8.dp)
        ,
        verticalAlignment = Alignment.CenterVertically

    ) {
        // 时间和地点信息区 (左侧 3/10)
        Column(
            modifier = Modifier.weight(3f)
            ,
            ) {
            Text(
                text = item.date.toString(),
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 4.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = getLastThreeElements(item.location),
                fontSize = 10.sp,
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

