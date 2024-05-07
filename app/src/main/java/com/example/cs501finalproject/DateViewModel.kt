package com.example.cs501finalproject


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID

class DateViewModel : ViewModel() {

    private val _startDate = MutableStateFlow(LocalDate.now().withDayOfYear(1))
    private val _endDate = MutableStateFlow(LocalDate.now())

    val startDate: StateFlow<LocalDate>
        get() = _startDate
    val endDate: StateFlow<LocalDate>
        get() = _startDate


    fun updateStartDate(date: LocalDate) {
        _startDate.value = date
    }

    fun updateEndDate(date: LocalDate) {
        _endDate.value = date
    }
    init {
        viewModelScope.launch {
            _startDate.collect { startDate ->
                startDate
            }
        }
        viewModelScope.launch {
            _endDate.collect { endDate ->
                endDate
            }
        }
    }

}