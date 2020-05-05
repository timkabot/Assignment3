package com.app.hw3.model.data

import android.content.Context
import com.app.hw3.domain.entity.Pet
import com.app.hw3.domain.entity.AnimalType
import com.app.hw3.domain.entity.Breed
import com.app.hw3.model.data.local.ApplicationDatabase
import com.app.hw3.model.data.server.PetFinderApi
import com.app.hw3.utils.internetAvailable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val api: PetFinderApi,
    db: ApplicationDatabase,
    private val context: Context
) {
    private val compositeDisposable = CompositeDisposable()
    private val petTypeDao = db.petTypeDao()
    private val petDao = db.petDao()
    private val breedDAO = db.breedDao()
    fun getAnimalTypes(): Single<List<AnimalType>> {
        if (internetAvailable(context)) {
            api.getAnimalTypes()
                .subscribeOn(Schedulers.io())
                .subscribeBy {
                    it.types.forEach { animalType ->
                        petTypeDao.insertAnimalType(animalType)
                    }
                }.addTo(compositeDisposable)
        }
        return petTypeDao.getAll()
    }

    fun getBreeds(petTypeName: String): Single<List<Breed>> {
        if (internetAvailable(context)) {
            api.getBreeds(petTypeName)
                .subscribeOn(Schedulers.io())
                .subscribeBy {
                    it.breeds.forEach {breed ->
                        breed.type = petTypeName
                        breedDAO.insertBreed(breed)
                    }
                }
                .addTo(compositeDisposable)
        }
        return breedDAO.getBreedsWithType(petTypeName)
    }


    fun getAnimals(type: String, breed: String) : Single<List<Pet>> {
        if(internetAvailable(context)){
            api.getAnimals(type, breed)
                .subscribeOn(Schedulers.io())
                .subscribeBy {
                    it.animals.forEach {  animal->
                        animal.breed = breed
                        animal.type = type
                        petDao.insertAnimal(animal)
                    }
                }
                .addTo(compositeDisposable)
        }
        return petDao.getAllWithTypeAndBreed(type, breed)
    }

    fun getAnimals() : Single<List<Pet>> {
        if(internetAvailable(context)){
            api.getAnimals()
                .subscribeOn(Schedulers.io())
                .subscribe {
                    it.animals.forEach {  animal->
                        petDao.insertAnimal(animal)
                    }
                }
                .addTo(compositeDisposable)
        }
        return petDao.getAll()
    }
}