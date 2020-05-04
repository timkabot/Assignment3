package com.app.assignment3.domain

import com.app.assignment3.model.data.AnimalRepository

class PetInteractor(private val animalRepository: AnimalRepository) {
    fun getAnimalTypes() = animalRepository.getAnimalTypes()
    fun getBreeds(animalTypeName: String) = animalRepository.getBreeds(animalTypeName)
    fun getAnimals(type:String, breed: String) = animalRepository.getAnimals(type,breed)
}