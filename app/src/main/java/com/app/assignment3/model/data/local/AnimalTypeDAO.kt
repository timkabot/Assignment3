package com.app.assignment3.model.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.app.assignment3.domain.entity.AnimalType
import io.reactivex.Single

@Dao
interface AnimalTypeDAO {


    @Query("SELECT * FROM AnimalType")
    fun getAll(): Single<List<AnimalType>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnimalType(animalType: AnimalType): Long

}