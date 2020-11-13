package com.example.sport_planet.model

data class ApplyListResponse(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : List<ApplyListModel>
)