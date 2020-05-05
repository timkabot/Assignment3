package com.app.hw3.domain.useCases

import com.app.hw3.model.data.AppRepository
import javax.inject.Inject

class PetUseCase @Inject constructor(private val appRepository: AppRepository) {
    fun getAnimals(type: String, breed: String) =
        appRepository.getAnimals(type, breed)
    fun getAnimals() =
        appRepository.getAnimals()
    fun getAnimalTypes() =
        appRepository.getAnimalTypes()
    fun getBreeds(animalTypeName: String) =
        appRepository.getBreeds(animalTypeName)


}