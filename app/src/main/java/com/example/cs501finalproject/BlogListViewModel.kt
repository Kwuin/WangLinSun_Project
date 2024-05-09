package com.example.cs501finalproject


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class BlogListViewModel : ViewModel() {
    private val blogRepository = BlogRepository.get()

    private val _blogs: MutableStateFlow<List<Blog>> = MutableStateFlow(emptyList())

    val blogs: StateFlow<List<Blog>>
        get() = _blogs.asStateFlow()

    init {
        viewModelScope.launch {
            blogRepository.getBlogs().collect {
                _blogs.value = it
            }
        }
    }

    suspend fun addBlog(blog: Blog) {
        blogRepository.addBlog(blog)
    }
    suspend fun getBlog(id: UUID):Blog {
        return blogRepository.getBlog(id)
    }

    fun deleteBlog(id: UUID){
        return blogRepository.deleteBlog(id)
    }

    val locations: StateFlow<List<String>> = _blogs
        .map { blogs -> blogs.map { it.location }.filter { it.isNotBlank() } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

}
