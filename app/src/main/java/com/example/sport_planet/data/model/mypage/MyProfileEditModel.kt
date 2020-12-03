package com.example.sport_planet.data.model.mypage

import com.example.sport_planet.data.response.basic.ExerciseResponse
import com.example.sport_planet.data.response.basic.RegionResponse

data class MyProfileEditModel(
    val userId : Long,
    val userName : String,
    val nickName : String,
    val email : String,
    val intro : String,
    val category : List<ExerciseResponse.Data>,
    val city : RegionResponse.Data
)