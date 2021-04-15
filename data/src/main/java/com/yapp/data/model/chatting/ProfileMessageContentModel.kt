package com.yapp.data.model.chatting

import com.google.gson.annotations.SerializedName

//@Serializable
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