package com.example.cs501finalproject.ui
import com.example.cs501finalproject.MapsActivity
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.cs501finalproject.R
import com.example.cs501finalproject.util.ImageViewModel
import android.graphics.Bitmap
import android.location.Geocoder
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material.Button
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.cs501finalproject.LocationViewModel
import com.example.cs501finalproject.util.ThemeManager
import com.google.android.gms.maps.model.LatLng

import java.io.File
import java.io.FileOutputStream
import java.util.Properties


@Composable
fun BlogView(navController: NavController, id: UUID, viewModel: LocationViewModel) {
    val blogListViewModel = BlogListViewModel()
    val blogState = remember { mutableStateOf<Blog?>(null) }
    val colors = ThemeManager.getAppThemeColors()
    val imageViewModel: ImageViewModel = viewModel()
    LaunchedEffect(id) {
        blogState.value = blogListViewModel.getBlog(id)
    }

    // Using the blog data to build the UI
    blogState.value?.let { blog ->
        Column(){
            BlogTop(blog, navController, Modifier, viewModel)
            BlogBody(blog, Modifier)

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
fun BlogTop(blog: Blog, navController:NavController, modifier: Modifier, viewModel: LocationViewModel) {
    val blogDetailViewModel : BlogDetailViewModel = viewModel(factory = BlogDetailViewModelFactory(blog.id))
    var text by remember { mutableStateOf(blog.title) }
    val context = LocalContext.current
    val activity = context as? Activity  // Cast context to Activity
    val geocoder = Geocoder(context)
    val colors = ThemeManager.getAppThemeColors()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(120.dp)
            .background(
                brush = Brush.linearGradient(
                    0f to colors.primary,
                    1f to colors.primaryVariant
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
                    .width(150.dp)
                    .background(Color.Transparent),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent, // Transparent background
                    cursorColor = Color.Black, // Customize the cursor color if needed
                    focusedIndicatorColor = Color.Transparent, // Hide the indicator below the text field when focused
                    unfocusedIndicatorColor = Color.Transparent, // Hide the indicator when not focused
                    textColor = Color.Black // Adjust text color as needed
                ),
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
                EmojiTextField(blogDetailViewModel, blog, Modifier)
                Spacer(modifier = Modifier.width(1.dp))
                ImagePickerButton(blogDetailViewModel, blog)
                Spacer(modifier = Modifier.width(1.dp))

                //MapButtonIcon(blogDetailViewModel, blog)
                Icon(
                    imageVector = Icons.Filled.Place,  // This is the material icon for a location
                    contentDescription = "Open Map",
                    modifier = Modifier
                        .padding(6.dp)  // Add padding around the icon for easier touching
                        .width(20.dp)
                        .clickable {
                            if (blog.location != "") {
                                val addressList = geocoder.getFromLocationName(blog.location, 1)
                                if (addressList != null && addressList.size > 0) {
                                    val address = addressList[0]
                                    val latLng = LatLng(address.latitude, address.longitude)
                                    viewModel.setInitLocation(latLng)
                                    viewModel.setLocation(latLng)
                                    viewModel.setUUID(blog.id)
                                }
                            } else {
                                viewModel.setInitLocation(LatLng(42.35, -71.10))
                                viewModel.setUUID(blog.id)
                            }

                            navController.navigate("map",)

                            // Use a request code to identify your request

                        },
                    tint = colors.secondaryVariant  // You can set the icon color
                )
                Spacer(modifier = Modifier.width(1.dp))

                DeleteButton(navController = navController, blog = blog)
            }
        }
    }
}

// Define a function to load properties
fun loadProperties(fileName: String): Properties {
    val props = Properties()
    val propFile = File(fileName)
    if (propFile.exists()) {
        propFile.inputStream().use {
            props.load(it)
        }
    }
    return props
}

@Composable
@Preview
fun check(){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.Filled.Place,  // This is the material icon for a location
            contentDescription = "Open Map",
            modifier = Modifier
                .padding(6.dp)  // Add padding around the icon for easier touching
                .width(30.dp)
            ,
            tint = Color.Red  // You can set the icon color
        )
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
fun BlogBody(blog: Blog, modifier: Modifier
) {
    val blogDetailViewModel : BlogDetailViewModel = viewModel(factory = BlogDetailViewModelFactory(blog.id))

    SimpleFilledTextFieldSample(blog, Modifier)
    DisplayText(blog, modifier)
    ImageDisplay(blogDetailViewModel)
}


@Composable
fun DisplayText(blog : Blog, modifier: Modifier) {
    Log.d("blog address", blog.location)


    blog.location.let { locationName ->
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(14.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = locationName, style = TextStyle(fontSize = 12.sp))
        }
    }
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
          colors = TextFieldDefaults.textFieldColors(
              backgroundColor = Color.Transparent, // Transparent background
              cursorColor = Color.Black, // Customize the cursor color if needed
              focusedIndicatorColor = Color.Transparent, // Hide the indicator below the text field when focused
              unfocusedIndicatorColor = Color.Transparent, // Hide the indicator when not focused
              textColor = Color.Black // Adjust text color as needed
        ),
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
fun DeleteButton(navController: NavController, blog:Blog){
    val blogListViewModel = BlogListViewModel()
    val colors = ThemeManager.getAppThemeColors()
    Icon(
        painter = painterResource(id = R.drawable.ic_delete),
        contentDescription = "Select Image",
        modifier = Modifier
            .width(50.dp)
            .clickable {
                blogListViewModel.deleteBlog(blog.id)
                navController.navigateUp()
            },
        tint = colors.secondaryVariant
    )
}

@Composable
fun ImagePickerButton(blogDetailViewModel: BlogDetailViewModel, blog:Blog) {
    val context = LocalContext.current
    val colors = ThemeManager.getAppThemeColors()
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

    Icon(
        painter = painterResource(id = R.drawable.ic_camera),
        contentDescription = "Select Image",
        modifier = Modifier
            .width(50.dp)
            .clickable {
                pickImageLauncher.launch("image/*")
            },
        tint = colors.secondaryVariant
    )

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


@Composable
fun EmojiTextField(blogDetailViewModel: BlogDetailViewModel, blog: Blog, modifier: Modifier) {
    var text by remember { mutableStateOf("\uD83D\uDE04") }

    TextField(
        value = text,
        modifier = modifier
            .width(50.dp)
            .background(Color.Transparent),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent, // Transparent background
            cursorColor = Color.Black, // Customize the cursor color if needed
            focusedIndicatorColor = Color.Transparent, // Hide the indicator below the text field when focused
            unfocusedIndicatorColor = Color.Transparent, // Hide the indicator when not focused
            textColor = Color.Black // Adjust text color as needed
        ),
        onValueChange = { newValue ->
            if (newValue.isEmpty() || isEmoji(newValue)) {
                text = newValue
            }
            blogDetailViewModel.updateBlog { currentBlog ->
                currentBlog.emoji = newValue
                currentBlog.copy()
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            capitalization = KeyboardCapitalization.Sentences,
            keyboardType = KeyboardType.Text,
            autoCorrect = true
        )
    )
}

fun isEmoji(input: String): Boolean {
    return input.all {
        Character.getType(it).toByte() == Character.SURROGATE.toByte() ||
                Character.getType(it).toByte() == Character.OTHER_SYMBOL.toByte()
    }
}

