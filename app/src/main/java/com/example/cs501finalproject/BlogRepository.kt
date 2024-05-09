package com.example.cs501finalproject

import android.content.Context
import androidx.room.Room
import com.example.cs501finalproject.database.BlogDatabase
import com.example.cs501finalproject.database.migration_1_2
import com.example.cs501finalproject.database.migration_2_3
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

import java.time.LocalDate
import java.util.UUID

private const val DATABASE_NAME = "blog-database"

class BlogRepository private constructor(
    context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope
) {

    private val database: BlogDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            BlogDatabase::class.java,
            DATABASE_NAME
        )
        .addMigrations(migration_1_2, migration_2_3)
        .fallbackToDestructiveMigration()
        .build()

    fun getBlogs(): Flow<List<Blog>> = database.blogDao().getBlogs()
    fun getDates(): Flow<List<LocalDate>> = database.blogDao().getDates()


    suspend fun getBlogOnDay(date: LocalDate): Flow<List<Blog>> = database.blogDao().getBlogOnDay(date)
    suspend fun getBlog(id: UUID): Blog = database.blogDao().getBlog(id)
    suspend fun getBlogOnTimeWindow(startDate: LocalDate, endDate: LocalDate): Flow<List<Blog>> = database.blogDao().getBlogOnTimeWindow(startDate, endDate)

    fun updateBlog(blog: Blog) {
        coroutineScope.launch {
            database.blogDao().updateBlog(blog)
        }
    }

    fun deleteBlog(id: UUID){
        coroutineScope.launch {
            database.blogDao().deleteBlog(id)
        }
    }
    suspend fun addBlog(blog: Blog) {
        database.blogDao().addBlog(blog)
    }

    companion object {
        private var INSTANCE: BlogRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = BlogRepository(context)
            }
        }

        fun get(): BlogRepository {
            return INSTANCE
                ?: throw IllegalStateException("BlogRepository must be initialized")
        }
    }
}
