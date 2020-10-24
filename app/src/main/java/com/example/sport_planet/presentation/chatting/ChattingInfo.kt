package com.example.sport_planet.presentation.chatting

object ChattingInfo { // 테스트용
    val MESSAGE_TYPE_TALK: String = "TALK"
    var SENDER_ID: Int = 1
    val URL: String = "ws://ec2-54-180-29-231.ap-northeast-2.compute.amazonaws.com:8080/ws/chat/websocket"
    var CHATROOM_ID: Int = 1

    fun settingChattingInfo(sender: Int, chatRoomId: Int){
        SENDER_ID = sender
        CHATROOM_ID = chatRoomId
    }
}