package com.example.sport_planet.model

import java.time.LocalDateTime

data class BoardResponse(
    val title: String,
    val userResponse: UserResponse,
    val categoryResponse: CategoryResponse,
    val addressResponse: AddressResponse,
    val tagResponse: TagResponse,
    val startsAt: LocalDateTime
)