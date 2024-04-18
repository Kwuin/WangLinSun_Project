package com.example.cs501finalproject.database

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

class BlogTypeConverters {
    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long): Date {
        return Date(millisSinceEpoch)
    }

    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    @TypeConverter
    fun fromLocalDate(value: LocalDate?): String? {
        return value?.format(formatter)
    }

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it, formatter) }
    }

}
