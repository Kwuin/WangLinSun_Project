package com.example.cs501finalproject.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.cs501finalproject.Blog

@Database(entities = [Blog::class], version = 3)
@TypeConverters(BlogTypeConverters::class)
abstract class BlogDatabase : RoomDatabase() {
    abstract fun blogDao(): BlogDao
}

val migration_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE Blog ADD COLUMN suspect TEXT NOT NULL DEFAULT ''"
        )
    }
}

val migration_2_3 = object : Migration(2,3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE Blog ADD COLUMN photoFileName TEXT"
        )
    }
}
