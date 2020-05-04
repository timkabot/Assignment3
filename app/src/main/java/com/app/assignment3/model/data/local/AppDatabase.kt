package com.app.assignment3.model.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.assignment3.domain.entity.Animal
import com.app.assignment3.domain.entity.AnimalType
import com.app.assignment3.domain.entity.Breed

@Database(entities = [AnimalType::class, Breed::class, Animal::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class AppDatabase : RoomDatabase(){
    companion object {
        fun build(context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "animals-db.db"
        ).build()
    }

    abstract fun animalTypeDao(): AnimalTypeDAO
    abstract fun breedDao() : BreedDAO
    abstract fun animalDao(): AnimalDAO
}