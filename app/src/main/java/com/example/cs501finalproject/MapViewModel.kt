package com.example.cs501finalproject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng


class MapViewModel : ViewModel() {
    private val _location = MutableLiveData<LatLng>()
    val location: LiveData<LatLng> = _location

    fun setLocation(latLng: LatLng) {
        _location.value = latLng
    }

    // Additional function to handle confirmation
    fun confirmLocation(description: String) {
        // Logic to handle location confirmation
        // This might involve updating the database or navigating to another screen
    }
}
