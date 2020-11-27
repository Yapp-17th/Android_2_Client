package com.example.sport_planet.data.response.mypage

import com.example.sport_planet.data.model.mypage.MyBookMarksModel

data class MyBookMarksResponse(
    val status: Int,
    val success : Boolean,
    val message : String,
    val data : List<MyBookMarksModel>
)