package com.example.sport_planet.data.response

data class UserResponse(
    val username: String,
    val nickname: String,
    val email: String,
    val accessToken: String,
    val addressResponse: AddressResponse,
    val categoryResponse: CategoryResponse
)