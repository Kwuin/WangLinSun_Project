package com.example.cs501finalproject.ui
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cs501finalproject.Blog
import com.example.cs501finalproject.BlogDetailViewModelFactory
import com.example.cs501finalproject.BlogListViewModel
import com.example.cs501finalproject.BlogDetailViewModel
import java.util.UUID
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.graphics.asImageBitmap
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import com.example.cs501finalproject.R
import java.io.InputStream

@Composable
fun BlogView(navController: NavController, id: UUID) {
    val blogListViewModel = BlogListViewModel()
    val blogState = remember { mutableStateOf<Blog?>(null) }

    LaunchedEffect(id) {
        blogState.value = blogListViewModel.getBlog(id)
    }

    // Using the blog data to build the UI
    blogState.value?.let { blog ->
        Column(){
            BlogTop(blog, navController, Modifier)
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
fun BlogTop(blog: Blog, navController:NavController, modifier: Modifier) {
    val blogDetailViewModel : BlogDetailViewModel = viewModel(factory = BlogDetailViewModelFactory(blog.id))
    var text by remember { mutableStateOf(blog.title) }
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
            TextField(
                value = text,
                modifier = modifier.width(200.dp).background(Color.Transparent),
                onValueChange = { text = it
                    blog.title = it
                    blogDetailViewModel.updateBlog { currentBlog ->
                        //Log.d("blog text", "${blog.text} and $it")
                        currentBlog.title = it
                        //Log.d("blog text", "current blog is ${currentBlog.text}")
                        currentBlog.copy()
                    }
                }
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                ImagePickerAndUploader()
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
    SimpleFilledTextFieldSample(blog, Modifier)
}
@Composable
fun SimpleFilledTextFieldSample(blog: Blog, modifier: Modifier) {
    var text by remember { mutableStateOf(blog.text) }
    val blogDetailViewModel : BlogDetailViewModel = viewModel(factory = BlogDetailViewModelFactory(blog.id))
    TextField(
        modifier = modifier.fillMaxWidth().background(Color.Transparent),
        value = text,
        onValueChange = { text = it
            blog.text = it
            blogDetailViewModel.updateBlog { currentBlog ->
                    Log.d("blog text", "${blog.text} and $it")
                    currentBlog.text = it
                    Log.d("blog text", "current blog is ${currentBlog.text}")
                    currentBlog.copy()
            }
        }
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


@Composable
fun ImagePickerAndUploader() {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            imageUri = uri
        }
    )

    // Icon button to trigger image picker
    IconShapedButton(
        iconPainter = painterResource(id = R.drawable.ic_camera),  // Ensure you have 'icon_image' in your drawables
        onClick = { imagePickerLauncher.launch("image/*") }
    )

    // Display the selected image
    imageUri?.let { uri ->
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val bitmap = inputStream?.use { android.graphics.BitmapFactory.decodeStream(it).asImageBitmap() }
        bitmap?.let {
            Image(
                bitmap = it,
                contentDescription = "Selected Image",
                modifier = androidx.compose.ui.Modifier.size(200.dp)  // Adjust size as necessary
            )
        }
    }
}


@Composable
fun IconShapedButton(iconPainter: Painter, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            painter = iconPainter,
            contentDescription = "Select Image",
            modifier = androidx.compose.ui.Modifier.size(50.dp)  // Set the size of the icon
        )
    }
}