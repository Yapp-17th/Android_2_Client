package com.example.sport_planet.presentation.chatting

object ChattingInfo { // 테스트용
    var SENDER_ID: Int = 2
    var CHATROOM_ID: Int = 0

    fun settingChattingInfo(sender: Int, chatRoomId: Int){
        SENDER_ID = sender
        CHATROOM_ID = chatRoomId
    }
}