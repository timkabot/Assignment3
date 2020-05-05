package com.app.hw3.model.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.hw3.domain.entity.Breed
import io.reactivex.Single

@Dao
interface BreedDao {

    @Query("SELECT * FROM Breed")
    fun getAll(): LiveData<List<Breed>>

    @Query("SELECT * FROM Breed WHERE type = :type")
    fun getBreedsWithType(type:String): Single<List<Breed>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBreed(breed: Breed): Long

}