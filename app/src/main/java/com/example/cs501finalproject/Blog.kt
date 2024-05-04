package com.example.cs501finalproject


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.UUID

@Entity
data class Blog(

    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var title: String,
    val date: LocalDate,
    var text: String = "",
    var photoFileName: String? = null,
    val location: String = "",
    var emoji: String = ""
)

