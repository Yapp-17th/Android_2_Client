package com.example.sport_planet.model.response


import com.google.gson.annotations.SerializedName

data class RegionResponse(
    @SerializedName("error")
    val error: Any,
    @SerializedName("result")
    val result: Result
) {
    data class Result(
        @SerializedName("data")
        val `data`: List<String>,
        @SerializedName("message")
        val message: String,
        @SerializedName("status")
        val status: Int,
        @SerializedName("type")
        val type: String
    )
}