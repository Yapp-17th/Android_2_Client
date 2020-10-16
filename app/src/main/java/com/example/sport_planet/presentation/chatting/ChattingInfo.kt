package com.example.sport_planet.presentation.chatting

object ChattingInfo {
    val MESSAGE_TYPE_TALK: String = "TALK"
    var SENDER: String = "DEFAULT"
    val URL: String = "ws://ec2-52-79-243-46.ap-northeast-2.compute.amazonaws.com:8080/ws/chat/websocket"
    var CHATROOM_ID: String = "0"

    fun set(sender: String, chatRoomId: String){
        SENDER = sender
        CHATROOM_ID = chatRoomId
    }
}