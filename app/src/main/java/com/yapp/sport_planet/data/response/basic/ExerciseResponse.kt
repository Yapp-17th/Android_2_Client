package com.yapp.sport_planet.data.response.basic


import android.os.Parcelable
import com.yapp.sport_planet.data.model.CommonApiModel
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ExerciseResponse(
    @SerializedName("data")
    val data: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("type")
    val type: String
) {
    @Parcelize
    data class Data(
        @SerializedName("id")
        val id: Long,
        @SerializedName("name")
        val name: String
    ) : Parcelable

    fun isSuccess(): Boolean {
        return status == 200
    }
}

fun ExerciseResponse.Data.toCommon(): CommonApiModel {
    return CommonApiModel(id, name)
}