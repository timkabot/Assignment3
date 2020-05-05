package com.app.hw3.model.data.server

import com.app.hw3.domain.entity.AnimalType

data class PetTypeNetworkResponse(
    val types: ArrayList<AnimalType>
)