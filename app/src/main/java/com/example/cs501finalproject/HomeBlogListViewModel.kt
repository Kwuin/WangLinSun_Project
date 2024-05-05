package com.example.cs501finalproject


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID

class HomeBlogListViewModel() : ViewModel() {
    private val blogRepository = BlogRepository.get()
    private val _startDate = MutableStateFlow(LocalDate.now().withDayOfYear(1))
    private val _endDate = MutableStateFlow(LocalDate.now())

    val startDate: StateFlow<LocalDate> = _startDate
    val endDate: StateFlow<LocalDate> = _endDate

    fun updateStartDate(date: LocalDate) {
        Log.d("HomeBlogListViewModel", "updateStartDate to $date")
        _startDate.value = date
    }

    fun updateEndDate(date: LocalDate) {
        Log.d("HomeBlogListViewModel", "updateEndDate to $date")
        _endDate.value = date
    }
    private val _blogs: MutableStateFlow<List<Blog>> = MutableStateFlow(emptyList())

    val blogs: StateFlow<List<Blog>>
        get() = _blogs.asStateFlow()

    init {
        viewModelScope.launch {
            combine(_startDate, _endDate) { start, end ->
                Pair(start, end)
            }.distinctUntilChanged().collect { (start, end) ->
                blogRepository.getBlogOnTimeWindow(start, end).collect {
                    _blogs.value = it
                }
            }
        }
    }

    suspend fun addBlog(blog: Blog) {
        blogRepository.addBlog(blog)
    }
}