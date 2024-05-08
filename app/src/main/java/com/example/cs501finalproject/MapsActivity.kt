// MapsActivity.kt
package com.example.cs501finalproject;
import android.app.Activity
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cs501finalproject.R.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.cs501finalproject.BlogDetailViewModelFactory
import java.util.UUID

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        private const val REQUEST_CODE_MAP = 123
    }

    private lateinit var mMap: GoogleMap
    private var lastSelectedLocation: LatLng? = null
    private lateinit var geocoder: Geocoder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activities_map)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val confirmButton = findViewById<Button>(id.confirm_button)
        confirmButton.setOnClickListener {
            lastSelectedLocation?.let { latLng ->
                val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
                val address = addresses?.firstOrNull()?.getAddressLine(0) ?: "Unknown location"
                val returnIntent = Intent()
                returnIntent.putExtra("location_name", address)
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }

        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val boston = LatLng(42.3, -71.0)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(boston, 10f))

        mMap.setOnMapClickListener { latLng ->
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(latLng).title("Selected Location"))
            val returnIntent = Intent()
            returnIntent.putExtra("latitude", latLng.latitude)
            returnIntent.putExtra("longitude", latLng.longitude)
            setResult(Activity.RESULT_OK, returnIntent)
            lastSelectedLocation = latLng
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_MAP && resultCode == Activity.RESULT_OK) {
            data?.let {
                val latitude = it.getDoubleExtra("latitude", 0.0)
                val longitude = it.getDoubleExtra("longitude", 0.0)
                val locationString = "$latitude,$longitude"
                val blogId = it.getStringExtra("blog_id",  )
                val uuid = UUID.fromString(blogId)
                //val blogDetailViewModel = BlogDetailViewModelFactory(uuid)
                val blogDetailViewModel: BlogDetailViewModel by viewModels {
                    BlogDetailViewModelFactory(uuid)
                }

                blogDetailViewModel.updateBlog { blog ->
                    blog.copy(location = locationString)
                }
            }
        }

    }

}
