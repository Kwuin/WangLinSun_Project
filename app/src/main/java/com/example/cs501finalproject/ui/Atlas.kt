package com.example.cs501finalproject.ui


import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.compose.material.Button
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cs501finalproject.LocationViewModel
import com.example.cs501finalproject.Blog
import com.example.cs501finalproject.BlogDetailViewModel
import com.example.cs501finalproject.BlogDetailViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cs501finalproject.BlogListViewModel
import com.google.android.gms.maps.model.LatLngBounds
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun AtlasPage(navController: NavController) {
    val context = LocalContext.current
    //val map: GoogleMap? by remember { mutableStateOf(null) }
    val blogListViewModel = BlogListViewModel()
    val locations = blogListViewModel.locations.collectAsState()

    val geocoder = Geocoder(context)


    AtlasViewComposable(modifier = Modifier,

        onMapReady = { googleMap ->
            var boundsBuilder = LatLngBounds.builder()

            locations.value.forEach{location ->
                val addressList = geocoder.getFromLocationName(location, 1)
                if (addressList != null && addressList.size > 0) {
                    val address = addressList[0]
                    val latLng = LatLng(address.latitude, address.longitude)
                    boundsBuilder = boundsBuilder.include(latLng)
                    googleMap.addMarker(MarkerOptions().position(latLng).title(getLastThreeElements(location)))
                }
            }

            val bounds = boundsBuilder.build()
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100)) // The second parameter is the padding

            // Log.d("equal location", init_location.value.toString())
           // googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(init_location.value, 10f))

        })
    // Button to confirm the location
}

//fun areLatLngsEqual(first: LatLng, second: LatLng, tolerance: Double = 0.00001): Boolean {
//    return Math.abs(first.latitude - second.latitude) < tolerance &&
//            Math.abs(first.longitude - second.longitude) < tolerance
//}

@Composable
fun AtlasViewComposable(modifier: Modifier = Modifier, onMapReady: (GoogleMap) -> Unit) {
    val mapView = rememberAtlasViewWithLifecycle()
    AndroidView({ mapView }) { mapView ->
        mapView.getMapAsync { googleMap ->
            onMapReady(googleMap)
        }
    }
}

@Composable
fun rememberAtlasViewWithLifecycle(): MapView {
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

//@Preview
//@Composable
//fun AtlasScreen(){
//    AtlasPage()
//}
//
