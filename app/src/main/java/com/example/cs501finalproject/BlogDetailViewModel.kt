package com.example.cs501finalproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class BlogDetailViewModel(blogId: UUID) : ViewModel() {
    private val blogRepository = BlogRepository.get()

    private val _blog: MutableStateFlow<Blog?> = MutableStateFlow(null)
    val blog: StateFlow<Blog?> = _blog.asStateFlow()

    init {
        viewModelScope.launch {
            _blog.value = blogRepository.getBlog(blogId)
        }
    }

    fun updateBlog(onUpdate: (Blog) -> Blog) {
        _blog.update { oldBlog ->
            oldBlog?.let { onUpdate(it) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        blog.value?.let { blogRepository.updateBlog(it) }
    }
}

class BlogDetailViewModelFactory(
    private val blogId: UUID
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BlogDetailViewModel(blogId) as T
    }
}