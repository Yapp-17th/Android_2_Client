package com.yapp.sport_planet.data.model.chatting

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileMessageContentModel (

    @SerializedName("senderNickname")
    val senderNickname: String,
    @SerializedName("like")
    val like: Int,
    @SerializedName("dislike")
    val dislike: Int,
    @SerializedName("intro")
    val intro: String

)