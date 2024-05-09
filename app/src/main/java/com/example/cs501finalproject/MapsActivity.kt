// MapsActivity.kt
package com.example.cs501finalproject;
import android.app.Activity
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.cs501finalproject.R.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.UUID

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        private const val REQUEST_CODE_MAP = 123
    }

    private lateinit var mMap: GoogleMap
    private var lastSelectedLocation: LatLng? = null
    private lateinit var geocoder: Geocoder
    var lastLocation: String? = null
    private lateinit var blogDetailViewModel: BlogDetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        val geocoder = Geocoder(this)
        super.onCreate(savedInstanceState)
        setContentView(layout.activities_map)
        lastLocation = intent.getStringExtra("last location")

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val confirmButton = findViewById<Button>(id.confirm_button)
        confirmButton.setOnClickListener {
            lastSelectedLocation?.let { latLng ->
                val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
                val address = addresses?.firstOrNull()?.getAddressLine(0) ?: "Unknown location"



                val blogId = intent.getStringExtra("blog_id" )
                intent.getStringExtra("date")?.let { it1 -> Log.d("blog date", it1) }
                val uuid = UUID.fromString(blogId)
                //val blogDetailViewModel = BlogDetailViewModelFactory(uuid)

//                val factory = BlogDetailViewModelFactory(uuid)
//                blogDetailViewModel = ViewModelProvider(this, factory).get(BlogDetailViewModel::class.java)

                Log.d("blogvidemodel date", "$uuid")

                val blogDetailViewModel: BlogDetailViewModel by viewModels {
                    BlogDetailViewModelFactory(uuid)
                }
                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        blogDetailViewModel.blog.collect { blog ->
                            if (blog != null) {
                                Log.d("update address", "${blog.id}")
                            }
                            blogDetailViewModel.updateBlog { blog ->
                                Log.d("update address", address)
                                blog.copy(location = address)
                            }
                        }
                    }
                }


                val returnIntent = Intent()

                returnIntent.putExtra("location_name", address)
                returnIntent.putExtra("blog_id", blogId)
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera

//        val boston = LatLng(42.3, -71.0)
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(boston, 10f))
        lastLocation?.let { geocodeAddressAndInitializeMap(it) }
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
        Log.d("onActivityResult", "running")
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
    fun geocodeAddressAndInitializeMap(addressString: String) {
        val geocoder = Geocoder(this)
        try {
            if(addressString.isNullOrEmpty()){
                val latLng = LatLng(42.5, -71.0 )
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
            }
            val addressList = geocoder.getFromLocationName(addressString, 1)
            if (addressList != null && addressList.size > 0) {
                val address = addressList[0]
                val latLng = LatLng(address.latitude, address.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
                mMap.addMarker(MarkerOptions().position(latLng).title(addressString))

            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}
