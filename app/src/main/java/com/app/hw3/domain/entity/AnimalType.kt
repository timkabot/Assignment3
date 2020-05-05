package com.app.hw3.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity

data class AnimalType(
    @PrimaryKey
    val name: String,
    val colors: ArrayList<String>,
    val coats: ArrayList<String>,
    val genders: ArrayList<String>
)