package com.example.sport_planet.data.response


data class MyViewEditRequest(
    val address: String,
    val category: List<String>,
    val email: String,
    val intro: String,
    val nickName: String,
    val userName: String
)