package com.yapp.sport_planet.data.response.mypage

import com.yapp.sport_planet.data.model.mypage.EvaluateListModel

data class EvaluateListResponse(
    val status : Int,
    val success : Boolean,
    val message: String,
    val data : List<EvaluateListModel>
)