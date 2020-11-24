package com.example.sport_planet.model.response


import com.example.sport_planet.model.RegionModel
import com.google.gson.annotations.SerializedName

data class RegionResponse(
    @SerializedName("error") val error: Any,
    @SerializedName("result") val regionModel: RegionModel
)
