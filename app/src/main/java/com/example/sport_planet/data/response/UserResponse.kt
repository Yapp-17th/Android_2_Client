package com.example.sport_planet.data.response

import com.example.sport_planet.data.response.basic.AddressResponse

data class UserResponse(
    val username: String,
    val nickname: String,
    val email: String,
    val accessToken: String,
    val addressResponse: AddressResponse,
    val categoryResponse: CategoryResponse
)