package com.example.cs501finalproject


import android.util.Log
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

    val startDate: MutableStateFlow<LocalDate>
        get() = _startDate
    val endDate: MutableStateFlow<LocalDate>
        get() = _endDate


    fun updateStartDate(date: LocalDate) {
        Log.d("DateVM", "Start Date is modified to $date")
        _startDate.value = date
    }

    fun updateEndDate(date: LocalDate) {
        Log.d("DateVM", "End Date is modified to $date")
        _endDate.value = date
    }

}