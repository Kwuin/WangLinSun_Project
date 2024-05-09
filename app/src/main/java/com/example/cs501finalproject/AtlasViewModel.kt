package com.example.cs501finalproject


import android.location.Geocoder
import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class AtlasViewModel : ViewModel() {
    private val blogRepository = BlogRepository.get()

    private val _blogs: MutableStateFlow<List<Blog>> = MutableStateFlow(emptyList())

    private val _locations: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())


    val blogs: StateFlow<List<Blog>>
        get() = _blogs.asStateFlow()


//    val locations: StateFlow<List<String>>
//        get() = _blogs
//            .map { blogs -> blogs.map { it.location } }
//            .stateIn(CoroutineScope(Dispatchers.Default), initialValue = listOf(), started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed())

    init {
        viewModelScope.launch {
            blogRepository.getBlogs().collect {
                _blogs.value = it
            }

            blogs.collect { blogs ->
                // Process each blog
                blogs.forEach { blog ->
                    val location = blog.location
                }
            }
        }

    }

}


