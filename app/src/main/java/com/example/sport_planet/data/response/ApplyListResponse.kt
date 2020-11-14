package com.example.sport_planet.data.response

import com.example.sport_planet.data.model.ApplyListModel

data class ApplyListResponse(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : List<ApplyListModel>
)