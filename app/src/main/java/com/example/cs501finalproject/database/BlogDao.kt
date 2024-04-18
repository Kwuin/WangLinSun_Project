package com.example.cs501finalproject.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.cs501finalproject.Blog
import kotlinx.coroutines.flow.Flow
import java.util.Date
import java.util.UUID

@Dao
interface BlogDao {
    @Query("SELECT * FROM Blog")
    fun getBlogs(): Flow<List<Blog>>

    @Query("SELECT * FROM Blog WHERE date=(:date)")
    fun getBlogonDay(date: Date): Flow<List<Blog>>

    @Query("SELECT * FROM blog WHERE id=(:id)")
    suspend fun getBlog(id: kotlin.Int): Blog

    @Update
    suspend fun updateBlog(blog: Blog)

    @Insert
    suspend fun addBlog(blog: Blog)
}
