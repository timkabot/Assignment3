package com.app.assignment3.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity

data class AnimalType(
    @PrimaryKey
    val name: String,
    val coats: ArrayList<String>,
    val colors: ArrayList<String>,
    val genders: ArrayList<String>
)