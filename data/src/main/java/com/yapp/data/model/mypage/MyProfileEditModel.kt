package com.yapp.data.model.mypage

import com.yapp.data.response.basic.ExerciseResponse
import com.yapp.data.response.basic.RegionResponse

data class MyProfileEditModel(
    val userId : Long,
    val userName : String,
    val nickName : String,
    val email : String,
    val intro : String,
    val category : List<ExerciseResponse.Data>,
    val city : RegionResponse.Data
)