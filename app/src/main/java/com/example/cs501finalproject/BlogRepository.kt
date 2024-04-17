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
        .build()

    fun getBlogs(): Flow<List<Blog>> = database.blogDao().getBlogs()

    suspend fun getBlog(id: UUID): Blog = database.blogDao().getBlog(id)

    fun updateBlog(blog: Blog) {
        coroutineScope.launch {
            database.blogDao().updateBlog(blog)
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
