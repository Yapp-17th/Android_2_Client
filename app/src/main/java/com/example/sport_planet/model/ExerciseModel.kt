package com.example.sport_planet.model

import com.google.gson.annotations.SerializedName

data class ExerciseModel(
        @SerializedName("data")
        val data: List<String>,
        @SerializedName("message")
        val message: String,
        @SerializedName("status")
        val status: Int,
        @SerializedName("type")
        val type: String
)