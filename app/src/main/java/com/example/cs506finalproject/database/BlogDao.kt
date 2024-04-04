package com.example.cs506finalproject.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.cs506finalproject.Blog
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface BlogDao {
    @Query("SELECT * FROM Blog")
    fun getBlog(): Flow<List<Blog>>

    @Query("SELECT * FROM blog WHERE id=(:id)")
    suspend fun getBlog(id: UUID): Blog

    @Update
    suspend fun updateBlog(blog: Blog)

    @Insert
    suspend fun addBlog(blog: Blog)
}
