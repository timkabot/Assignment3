package com.app.assignment3.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Breed(
    @PrimaryKey
    val name: String,
    var type: String
)