package com.app.assignment3.model.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.app.assignment3.domain.entity.Animal
import com.app.assignment3.domain.entity.AnimalType
import com.app.assignment3.domain.entity.Breed
import com.app.assignment3.model.data.local.AppDatabase
import com.app.assignment3.model.data.server.ApiService
import com.app.assignment3.utils.hasInternet
import com.github.ajalt.timberkt.Timber
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class AnimalRepository(
    private val api: ApiService,
    db: AppDatabase,
    private val context: Context
) {
    private val compositeDisposable = CompositeDisposable()
    private val animalTypeDao = db.animalTypeDao()
    private val animalDao = db.animalDao()
    private val breedDAO = db.breedDao()
    fun getAnimalTypes(): Single<List<AnimalType>> {
        if (hasInternet(context)) {
            api.getAnimalTypes()
                .subscribeOn(Schedulers.io())
                .doOnError {  Timber.d{"Animal type request error: ${it.message}"} }
                .subscribe {
                    it.types.forEach { animalType ->
                        animalTypeDao.insertAnimalType(animalType)
                    }
                }.addTo(compositeDisposable)
        }
        return animalTypeDao.getAll()
    }

    fun getBreeds(animalTypeName: String): Single<List<Breed>> {
        if (hasInternet(context)) {
            api.getBreeds(animalTypeName)
                .subscribeOn(Schedulers.io())
                .doOnError {  Timber.d{"Breeds request error with type: $animalTypeName request error: ${it.message}"} }
                .subscribe {
                    it.breeds.forEach {breed ->
                        breed.type = animalTypeName
                        breedDAO.insertBreed(breed)
                    }
                }
                .addTo(compositeDisposable)
        }
        return breedDAO.getBreedsWithType(animalTypeName)
    }


    fun getAnimals(type: String, breed: String) : Single<List<Animal>> {
        if(hasInternet(context)){
            api.getAnimals(type, breed)
                .subscribeOn(Schedulers.io())
                .doOnError {  Timber.d{"Animals request error with type: $type and breed: $breed request error: ${it.message}" }}
                .subscribe {
                    it.animals.forEach {  animal->
                        animal.breed = breed
                        animal.type = type
                        animalDao.insertAnimal(animal)
                    }
                }
                .addTo(compositeDisposable)
        }
        return animalDao.getAllWithTypeAndBreed(type, breed)
    }

    fun getAnimals() : Single<List<Animal>> {
        if(hasInternet(context)){
            api.getAnimals()
                .subscribeOn(Schedulers.io())
                .doOnError {  Timber.d{"Animals request error  request error: ${it.message}" }}
                .subscribe {
                    it.animals.forEach {  animal->
                        animalDao.insertAnimal(animal)
                    }
                }
                .addTo(compositeDisposable)
        }
        return animalDao.getAll()
    }
}