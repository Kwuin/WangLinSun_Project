package com.example.cs501finalproject

import android.location.Geocoder
import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.UUID

class LocationViewModel : ViewModel() {
    val init_loc = LatLng(42.35, -71.10)
    private val _location = MutableStateFlow(init_loc)
    val location: MutableStateFlow<LatLng> = _location
    private val _UUID = MutableStateFlow(java.util.UUID.randomUUID())
    val UUID: MutableStateFlow<UUID> = _UUID
    private val _init_location = MutableStateFlow(init_loc)
    val init_location: MutableStateFlow<LatLng> = _init_location

    fun setLocation(location: LatLng) {
        _location.value = location
    }

    fun setInitLocation(location: LatLng) {
        _init_location.value = location
    }
    fun setUUID(id: UUID) {
        _UUID.value = id
    }

    // Additional function to handle confirmation
    fun confirmLocation(description: String) {
        // Logic to handle location confirmation
        // This might involve updating the database or navigating to another screen
    }
}
