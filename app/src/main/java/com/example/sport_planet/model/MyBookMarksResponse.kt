package com.example.sport_planet.model

data class MyBookMarksResponse(
    val status: Int,
    val success : Boolean,
    val message : String,
    val data : List<MyBookMarksModel>
)