package com.example.cs501finalproject.ui
import com.example.cs501finalproject.MapsActivity
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material.Button
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.model.LatLng

import java.io.File
import java.io.FileOutputStream
import java.util.Properties


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
    var text by remember { mutableStateOf(blog.title) }
    val context = LocalContext.current
    val activity = context as? Activity  // Cast context to Activity

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
                ImagePickerButton(blogDetailViewModel, blog)
                Spacer(modifier = Modifier.width(5.dp))
                EmojiTextField(blogDetailViewModel, blog, Modifier)
                Spacer(modifier = Modifier.width(5.dp))
                //MapButtonIcon(blogDetailViewModel, blog)
                Icon(
                    imageVector = Icons.Filled.Place,  // This is the material icon for a location
                    contentDescription = "Open Map",
                    modifier = Modifier
                        .padding(6.dp)  // Add padding around the icon for easier touching
                        .width(50.dp)
                        .clickable {
                            activity?.let {
                                val intent = Intent(it, MapsActivity::class.java)
                                intent.putExtra("last location", blog.location)
                                Log.d("id before intent", blog.id.toString())
                                intent.putExtra("date",blog.date.toString())
                                intent.putExtra("blog_id", blog.id.toString())
                                Log.d("last location",blog.location)

                                it.startActivityForResult(intent, 123)  // Use a request code to identify your request
                            }
                        },
                    tint = Color.Red  // You can set the icon color
                )
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
fun BlogBody(blog: Blog
) {
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

@Composable
fun MapButton(blogDetailViewModel: BlogDetailViewModel, blog: Blog){
    // Load your secrets
    val secrets = loadProperties("secrets.properties")

// Accessing a property
    val googleMapsApiKey = secrets.getProperty("GOOGLE_MAPS_API_KEY", "")
    val context = LocalContext.current
    val activity = context as? Activity  // Cast context to Activity

    val intent = Intent(activity, MapsActivity::class.java)
    activity?.startActivityForResult(intent, 123) // Use a request code to identify your request


}

@Composable
fun MapButtonIcon(blogDetailViewModel: BlogDetailViewModel, blog: Blog) {
    // Obtain the current activity context from the LocalContext
    val context = LocalContext.current
    val activity = context as? Activity  // Cast context to Activity

    Icon(
        imageVector = Icons.Filled.Place,  // This is the material icon for a location
        contentDescription = "Open Map",
        modifier = Modifier
            .padding(16.dp)  // Add padding around the icon for easier touching
            .clickable {
                // Ensure activity is not null
                activity?.let {
                    val intent = Intent(it, MapsActivity::class.java)
                    it.startActivityForResult(
                        intent,
                        123
                    )  // Use a request code to identify your request
                }
            },
        tint = Color.Red  // You can set the icon color
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

