package com.example.makeitso.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: String,
    val name: String,
    val login: String
)
