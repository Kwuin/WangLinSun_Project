package com.example.cs501finalproject.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.cs501finalproject.Blog
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.util.Date
import java.util.UUID

@Dao
interface BlogDao {
    @Query("SELECT * FROM Blog")
    fun getBlogs(): Flow<List<Blog>>

    @Query("SELECT * FROM Blog WHERE date=(:date)")
    fun getBlogOnDay(date: LocalDate): Flow<List<Blog>>

    @Query("SELECT * FROM blog WHERE id=(:id)")
    suspend fun getBlog(id: UUID): Blog

    @Update
    suspend fun updateBlog(blog: Blog)

    @Insert
    suspend fun addBlog(blog: Blog)
    @Query("SELECT * FROM Blog WHERE date>=(:startDate) and date<=(:endDate) ORDER BY date DESC")
    fun getBlogOnTimeWindow(startDate: LocalDate, endDate: LocalDate): Flow<List<Blog>>
}
