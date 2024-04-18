package com.example.cs501finalproject


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.UUID

@Entity
data class Blog(
    @PrimaryKey val id: Int,
    val title: String = "",
    val date: LocalDate = LocalDate.now(),
    var text: String = "",
    val photoFileName: String? = null,
    val location: String = "",
    val emoji: String = ""
)

