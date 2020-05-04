package com.app.assignment3.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Animal(
    @PrimaryKey
    val id: Long,
    val url: String?,
    var type: String?,
    var breed: String?,
    val colors: Colors,
    val age: String?,
    val gender: String?,
    val size: String?,
    val coat: String?,
    val name: String,
    val photos: ArrayList<Photos>?,
    val description: String?
) : Serializable