package com.yapp.sport_planet.data.response.mypage

import com.yapp.sport_planet.data.model.mypage.ApplyListModel

data class ApplyListResponse(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : List<ApplyListModel>
)