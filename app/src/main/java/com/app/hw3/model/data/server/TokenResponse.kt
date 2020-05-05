package com.app.hw3.model.data.server

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("token_type")
    private val tokenType: String,
    @SerializedName("expires_in")
    private val expiresIn: Long,
    @SerializedName("access_token") val accessToken: String
)