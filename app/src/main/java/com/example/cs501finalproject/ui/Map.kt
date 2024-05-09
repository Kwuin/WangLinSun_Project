package com.example.cs501finalproject.ui

import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cs501finalproject.LocationViewModel
import com.example.cs501finalproject.Blog
import com.example.cs501finalproject.BlogDetailViewModel
import com.example.cs501finalproject.BlogDetailViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cs501finalproject.util.ThemeManager
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun MapPage(navController: NavController, locationviewModel: LocationViewModel) {
    val context = LocalContext.current
    //val map: GoogleMap? by remember { mutableStateOf(null) }
    val init_location = locationviewModel.init_location.collectAsState()
    val location = locationviewModel.location.collectAsState()
    val geocoder = Geocoder(context)
    val addresses = geocoder.getFromLocation(location.value.latitude, location.value.longitude, 1)
    val address = addresses?.firstOrNull()?.getAddressLine(0) ?: "Unknown location"
    val id = locationviewModel.UUID.collectAsState()
    val blogDetailViewModel : BlogDetailViewModel = viewModel(factory = BlogDetailViewModelFactory(id.value))
    val colors = ThemeManager.getAppThemeColors()



    MapViewComposable(modifier = Modifier,

        onMapReady = { googleMap ->
            Log.d("initial location", init_location.value.toString())
            Log.d("location", location.value.toString())
            if (areLatLngsEqual(init_location.value, location.value)){
                Log.d("equal location", init_location.value.toString())
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(init_location.value, 10f))
                googleMap.addMarker(MarkerOptions().position(init_location.value).title(address))
            }


        googleMap.setOnMapClickListener { latLng ->
            googleMap.clear()
            googleMap.addMarker(MarkerOptions().position(latLng).title("Selected Location"))
            locationviewModel.setLocation(latLng)
        }
    })

    // Button to confirm the location
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
        ,

    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .background(color = Color.Transparent)
        ) {
            Button(
                modifier = Modifier
                    .background(color = Color.Transparent)
                    .align(Alignment.BottomCenter),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colors.secondaryVariant,  // Background color of the button
                    contentColor = colors.onPrimary     // Color of the content (text/icon) inside the button
                ),
                onClick = {
                    location.let {
                        locationviewModel.confirmLocation("Location confirmed with description")
                        locationviewModel.setLocation(it.value)
                        val newAddresses =
                            geocoder.getFromLocation(it.value.latitude, it.value.longitude, 1)
                        val newAddress =
                            addresses?.firstOrNull()?.getAddressLine(0) ?: "Unknown location"


                        blogDetailViewModel.updateBlog { blog ->
                            Log.d("update address", address)
                            blog.copy(location = newAddress)
                        }
                        navController.popBackStack()
                    }
                },
            ) {
                Text("Confirm Location")
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .background(color = Color.Transparent)
        ) {
            Button(
                modifier = Modifier
                    .background(color = Color.Transparent)
                    .align(Alignment.BottomCenter),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colors.secondaryVariant,  // Background color of the button
                    contentColor = colors.onPrimary     // Color of the content (text/icon) inside the button
                ),
                onClick = {
                    navController.popBackStack()
                },
            ) {
                Text("Back")
            }
        }
    }
}

fun areLatLngsEqual(first: LatLng, second: LatLng, tolerance: Double = 0.00001): Boolean {
    return Math.abs(first.latitude - second.latitude) < tolerance &&
            Math.abs(first.longitude - second.longitude) < tolerance
}

@Composable
fun MapViewComposable(modifier: Modifier = Modifier, onMapReady: (GoogleMap) -> Unit) {
    val mapView = rememberMapViewWithLifecycle()
    AndroidView({ mapView }) { mapView ->
        mapView.getMapAsync { googleMap ->
            onMapReady(googleMap)
        }
    }
}

@Composable
fun rememberMapViewWithLifecycle(): MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            onCreate(Bundle())
            onResume()
        }
    }
    DisposableEffect(mapView) {
        onDispose {
            mapView.onPause()
            mapView.onDestroy()
        }
    }
    return mapView
}
