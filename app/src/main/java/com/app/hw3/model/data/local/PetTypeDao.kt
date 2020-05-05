package com.app.hw3.model.data.local

import androidx.room.*
import com.app.hw3.domain.entity.AnimalType
import io.reactivex.Single

@Dao
interface PetTypeDao {


    @Query("SELECT * FROM AnimalType")
    fun getAll(): Single<List<AnimalType>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnimalType(animalType: AnimalType): Long

}