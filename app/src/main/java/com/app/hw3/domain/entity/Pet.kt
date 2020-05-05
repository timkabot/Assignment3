package com.app.hw3.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Pet(
    @PrimaryKey
    val id: Long,
    val url: String?,
    var type: String?,
    var breed: String?,
    val colors: Colors,
    val age: String?,
    val gender: String?,
    val size: String?,
    val name: String,
    val photos: ArrayList<Photos>?,
    val description: String?
) : Serializable