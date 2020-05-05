package com.app.hw3.model.data.server

import com.app.hw3.domain.entity.Pet

data class PetListNetworkResponse(val animals: List<Pet>)