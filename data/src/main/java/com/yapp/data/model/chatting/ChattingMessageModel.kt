package com.yapp.data.model.chatting

import com.google.gson.annotations.SerializedName

//
//@Parcelize
//@Serializable
data class ChattingMessageModel(

    @SerializedName("content")
    val content: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("messageId")
    val messageId: Long,
    @SerializedName("senderId")
    val senderId: Long,
    @SerializedName("senderNickname")
    val senderNickname: String,
    @SerializedName("createdDate")
    var createdDate: String,
    @SerializedName("createdTime")
    var createdTime: String,
    @SerializedName("isSameDate")
    var isSameDate: Boolean,
    @SerializedName("isSameTime")
    var isSameTime: Int

)