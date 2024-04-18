package com.example.cs501finalproject


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity
data class Blog(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val title: String = "",
    val date: Date = Date(),
    var text: String = "",
    val photoFileName: String? = null,
    val location: String = "",
    val emoji: String = ""
)
