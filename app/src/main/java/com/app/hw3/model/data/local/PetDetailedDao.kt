package com.app.hw3.model.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.hw3.domain.entity.Pet
import io.reactivex.Single

@Dao
interface PetDetailedDao {


    @Query("SELECT * FROM Pet")
    fun getAll(): Single<List<Pet>>

    @Query("SELECT * FROM Pet WHERE type=:type and breed=:breed")
    fun getAllWithTypeAndBreed(type:String, breed: String): Single<List<Pet>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnimal(animal: Pet): Long

}