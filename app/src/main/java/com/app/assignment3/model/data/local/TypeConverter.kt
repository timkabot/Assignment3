package com.app.assignment3.model.data.local

import androidx.room.TypeConverter
import com.app.assignment3.domain.entity.Colors
import com.app.assignment3.domain.entity.Photos
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class TypeConverter {

    @TypeConverter
    fun fromString(value: String): ArrayList<String> {
        val mapType = object : TypeToken<ArrayList<String>>() {}.type
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    fun fromStringArraylist(list: ArrayList<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromPhotos(photos: ArrayList<Photos>) : String{
        val gson = Gson()
        return gson.toJson(photos)
    }

    @TypeConverter
    fun fromStringToPhotos(value: String) : ArrayList<Photos>{
        val mapType = object : TypeToken< ArrayList<Photos>>() {}.type
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    fun fromColors(colors: Colors) : String{
        val gson = Gson()
        return gson.toJson(colors)
    }

    @TypeConverter
    fun fromStringToColors(value: String) : Colors{
        val mapType = object : TypeToken<Colors>() {}.type
        return Gson().fromJson(value, mapType)
    }
}