package com.app.hw3.model.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.hw3.domain.entity.Pet
import com.app.hw3.domain.entity.AnimalType
import com.app.hw3.domain.entity.Breed

@Database(entities = [ Pet::class, AnimalType::class, Breed::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class ApplicationDatabase : RoomDatabase(){
    companion object {
        fun build(context: Context) = Room.databaseBuilder(
            context,
            ApplicationDatabase::class.java,
            "application-database.db"
        ).build()
    }

    abstract fun petTypeDao(): PetTypeDao
    abstract fun breedDao() : BreedDao
    abstract fun petDao(): PetDetailedDao
}