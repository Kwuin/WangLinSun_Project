package com.example.cs501finalproject.ui
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cs501finalproject.Blog
import com.example.cs501finalproject.HomeBlogDetailViewModel
import com.example.cs501finalproject.HomeBlogListViewModel
import java.util.UUID

@Composable
fun BlogView(navController: NavController, id: UUID) {
    val blogListViewModel = HomeBlogListViewModel()
    val factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeBlogDetailViewModel(id) as T
        }
    }
    val blogState = remember { mutableStateOf<Blog?>(null) }

    LaunchedEffect(id) {
        blogState.value = blogListViewModel.getBlog(id)
    }

    // Using the blog data to build the UI
    blogState.value?.let { blog ->
        Column(){
            BlogTop(blog, navController)
            BlogBody(blog)
//            //NavigationBar(navController)
        }
    }

//    LaunchedEffect(id) {
//        thisblog = blogListViewModel.getBlog(id)
//        // Consider handling navigation differently, as navigating inside LaunchedEffect like this might not be ideal
//
//
//
//    }
    //val blog = blogListViewModel.getBlog(id)



}



@Composable
fun BlogTop(blog: Blog, navController:NavController) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(120.dp)
            .background(
                brush = Brush.linearGradient(
                    0f to Color(0xffe6d6f0),
                    1f to Color(0xff9f88ae),
                )
            )
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BackButton(navController)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}


@Composable
fun BackButton(navController: NavController) {
    IconButton(
        onClick = { navController.navigateUp() },
        modifier = Modifier.padding(start = 16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier.size(24.dp)
        )
    }
}


@Composable
fun BlogBody(blog: Blog
) {
    SimpleFilledTextFieldSample(blog)

}
@Composable
fun SimpleFilledTextFieldSample(blog: Blog) {
    var text by remember { mutableStateOf(blog.text) }
    val blogDetailViewModel = HomeBlogDetailViewModel(blog.id)
    TextField(
        value = text,
        onValueChange = { text = it
            blog.text = it
            blogDetailViewModel.updateBlog { currentBlog ->
                if (currentBlog.id == blog.id) {
                    currentBlog.copy(text = it)
                } else {
                    currentBlog
                }
            }
        },
        label = { Text("Label") }
    )
}


//@Preview
//@Composable
//fun PreviewBlog() {
//    val numericString = "1"
//    //val uuid = UUID.nameUUIDFromBytes(numericString.toByteArray())
//
//    BlogView(rememberNavController(), )
//}

//@Composable
//fun BlogScreen(navController: NavController, blogId: UUID) {
//
//
//    LaunchedEffect(blogId) {
//        blogListViewModel.getBlog(blogId)
//        // Consider handling navigation differently, as navigating inside LaunchedEffect like this might not be ideal
//        BlogView(navController = navController, blogViewModel = blogListViewModel, id = blogID)
//    }
//
//}