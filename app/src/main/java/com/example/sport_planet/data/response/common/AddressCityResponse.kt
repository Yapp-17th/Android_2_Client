package com.example.sport_planet.data.response.common


import com.example.sport_planet.data.model.AddressCityModel

data class AddressCityResponse(
    val type: String,
    val status: Int,
    val message: String,
    val data: List<AddressCityModel>
) {
    fun isSuccess(): Boolean = message == "SUCCESS"
}