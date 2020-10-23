package com.example.sport_planet.model

data class User(
    val username: String,
    val nickname: String,
    val email: String,
    val accessToken: String,
    val address: Address,
    val category: Category
)