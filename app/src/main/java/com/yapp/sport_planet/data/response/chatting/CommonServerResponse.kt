package com.yapp.sport_planet.data.response.chatting

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CommonServerResponse (
    @SerializedName("transactionTime")
    val transactionTime: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("responseType")
    val responseType: String,
    @SerializedName("message")
    val message: String
): Parcelable