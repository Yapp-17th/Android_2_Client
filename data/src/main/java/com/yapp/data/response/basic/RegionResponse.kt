package com.yapp.data.response.basic


import com.google.gson.annotations.SerializedName

data class RegionResponse(
    @SerializedName("data")
    val data: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("type")
    val type: String
) {
    //    @Parcelize
    data class Data(
        @SerializedName("id")
        val id: Long,
        @SerializedName("name")
        val name: String
    )
//
//    fun isSuccess(): Boolean {
//        return status == 200
//    }
}
//
//fun RegionResponse.Data.toCommon(): CommonApiModel {
//    return CommonApiModel(id, name)
//}