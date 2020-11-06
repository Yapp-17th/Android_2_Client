package com.example.sport_planet.presentation.chatting

object ChattingInfo { // 테스트용
    var USER_ID: Long = 1
    var CHATROOM_ID: Long = 0

    fun settingChattingInfo(chatRoomId: Long){
        CHATROOM_ID = chatRoomId
    }
}