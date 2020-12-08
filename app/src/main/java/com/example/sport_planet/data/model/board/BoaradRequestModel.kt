package com.example.sport_planet.data.model.board

import com.example.sport_planet.data.enums.TimeFilterEnum

data class BoardRequestModel(
    val category: String = "0",
    val address: String = "0",
    val sorting: TimeFilterEnum = TimeFilterEnum.TIME_LATEST
)