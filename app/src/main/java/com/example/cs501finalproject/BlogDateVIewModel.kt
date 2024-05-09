package com.example.cs501finalproject


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class BlogDateVIewModel : ViewModel() {
    private val blogRepository = BlogRepository.get()

    private val _addresses: MutableStateFlow<Map<Blog, Boolean>> = MutableStateFlow(mutableMapOf())

    val addresses: StateFlow<Map<Blog, Boolean>>
        get() = _addresses.asStateFlow()

    init {
        viewModelScope.launch {
            blogRepository.getBlogs().collect {
                _addresses.value = it.associateWith { true }
            }
        }
    }

    suspend fun addBlog(blog: Blog) {
        blogRepository.addBlog(blog)
    }
    suspend fun getBlog(id: UUID):Blog {
        return blogRepository.getBlog(id)
    }

}