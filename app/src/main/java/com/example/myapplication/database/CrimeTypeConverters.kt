package com.example.cs506finalproject.database

import androidx.room.TypeConverter
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
}
