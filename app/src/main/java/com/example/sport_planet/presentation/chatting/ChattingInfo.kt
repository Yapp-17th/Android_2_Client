package com.example.sport_planet.presentation.chatting

object ChattingInfo { // 테스트용
    var USER_ID: Int = 2
    var CHATROOM_ID: Int = 0

    fun settingChattingInfo(user: Int, chatRoomId: Int){
        USER_ID = user
        CHATROOM_ID = chatRoomId
    }
}