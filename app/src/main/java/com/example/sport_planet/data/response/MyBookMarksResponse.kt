package com.example.sport_planet.data.response

import com.example.sport_planet.data.model.MyBookMarksModel

data class MyBookMarksResponse(
    val status: Int,
    val success : Boolean,
    val message : String,
    val data : List<MyBookMarksModel>
)