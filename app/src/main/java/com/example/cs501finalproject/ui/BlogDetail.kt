package com.example.cs501finalproject.ui
import android.Manifest
import android.content.Context
import android.graphics.BitmapFactory
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.graphics.asImageBitmap
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.net.toUri
import com.example.cs501finalproject.R
import com.example.cs501finalproject.util.ImageViewModel
import java.io.FileNotFoundException
import java.io.InputStream
import android.graphics.Bitmap

import androidx.compose.material.Button

import java.io.File
import java.io.FileOutputStream

@Composable
fun BlogView(navController: NavController, id: UUID) {
    val blogListViewModel = BlogListViewModel()
    val blogState = remember { mutableStateOf<Blog?>(null) }
    val imageViewModel: ImageViewModel = viewModel()
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
    val imageViewModel: ImageViewModel = viewModel()
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
                modifier = modifier
                    .width(200.dp)
                    .background(Color.Transparent),
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
                ImagePickerButton(blogDetailViewModel, blog)
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
    val imageViewModel: ImageViewModel = viewModel()
    val blogDetailViewModel : BlogDetailViewModel = viewModel(factory = BlogDetailViewModelFactory(blog.id))

    SimpleFilledTextFieldSample(blog, Modifier)

    ImageDisplay(blogDetailViewModel)
}
@Composable
fun SimpleFilledTextFieldSample(blog: Blog, modifier: Modifier) {
    val blogDetailViewModel : BlogDetailViewModel = viewModel(factory = BlogDetailViewModelFactory(blog.id))

    var text by remember { mutableStateOf(blog.text) }
      TextField(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(Color.Transparent),
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
fun ImagePickerButton(blogDetailViewModel: BlogDetailViewModel, blog:Blog) {
    val context = LocalContext.current
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                // Save the selected image to internal storage and update the blog object
                val photoFile = saveImageToLocalFile(uri, context, "photo_${blog.id}.jpg")
                blogDetailViewModel.updateBlog { blog -> blog.copy(photoFileName = photoFile?.absolutePath) }
            }
        }
    )

    Button(onClick = { pickImageLauncher.launch("image/*") }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_camera),
            contentDescription = "Select Image"
        )
    }
}

fun saveImageToLocalFile(uri: Uri, context: Context, fileName: String): File? {
    val inputStream = context.contentResolver.openInputStream(uri)
    val bitmap = BitmapFactory.decodeStream(inputStream)
    val outputFile = File(context.filesDir, fileName)
    val fileOutputStream = FileOutputStream(outputFile)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
    fileOutputStream.close()
    return outputFile
}

@Composable
fun ImageDisplay(blogDetailViewModel: BlogDetailViewModel) {
    val blog by blogDetailViewModel.blog.collectAsState()

    blog?.photoFileName?.let { fileName ->
        val imageFile = File(fileName)
        if (imageFile.exists()) {
            val bitmap = BitmapFactory.decodeFile(fileName).asImageBitmap()
            Image(
                bitmap = bitmap,
                contentDescription = "Selected Image",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}



