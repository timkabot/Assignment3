package com.app.assignment3.model.data.server.response

import com.app.assignment3.domain.entity.AnimalType

data class AnimalTypesResponse(
    val types: ArrayList<AnimalType>
)