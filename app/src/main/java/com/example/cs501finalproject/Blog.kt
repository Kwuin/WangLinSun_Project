package com.example.cs501finalproject


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity
data class Blog(
    @PrimaryKey val id: UUID,
    val title: String,
    val date: Date,
<<<<<<< HEAD
    var text: String = "",
    val photoFileName: String? = null
=======
    val text: String = "",
    val photoFileName: String? = null,
    val location: String = "",
    val emoji: String = ""
>>>>>>> 2e121cc405fec55d13f9bc312dd79b73fe3389b1
)
