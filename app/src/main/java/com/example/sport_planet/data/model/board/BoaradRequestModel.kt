package com.example.sport_planet.data.model.board

import com.example.sport_planet.data.enums.TimeFilterEnum
import com.example.sport_planet.data.response.basic.ExerciseResponse
import com.example.sport_planet.data.response.basic.RegionResponse

data class BoardRequestModel(
    val category: List<ExerciseResponse.Data> = emptyList(),
    val address: List<RegionResponse.Data> = emptyList(),
    val sorting: TimeFilterEnum = TimeFilterEnum.TIME_LATEST
)