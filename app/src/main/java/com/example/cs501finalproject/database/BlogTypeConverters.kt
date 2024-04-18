package com.example.cs501finalproject.database

import androidx.room.TypeConverter

import com.kizitonwose.calendar.core.CalendarDay
import java.time.LocalDate
import java.util.Date

class BlogTypeConverters {
    @TypeConverter
    fun fromDate(date: LocalDate): Long {
        return date.toEpochDay()
    }

    @TypeConverter
    fun toDate(epochDay: Long): LocalDate {
        return LocalDate.ofEpochDay(epochDay)
    }

}
