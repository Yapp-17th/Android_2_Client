package com.yapp.data.model.board

import com.yapp.data.response.basic.ExerciseResponse
import com.yapp.data.response.basic.RegionResponse
import com.yapp.sport_planet.data.enums.TimeFilterEnum

data class BoardRequestModel(
    val category: List<ExerciseResponse.Data> = emptyList(),
    val address: List<RegionResponse.Data> = emptyList(),
    val sorting: TimeFilterEnum = TimeFilterEnum.TIME_LATEST
)