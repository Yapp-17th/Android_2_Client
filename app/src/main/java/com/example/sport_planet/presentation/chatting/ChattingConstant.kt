package com.example.sport_planet.presentation.chatting

object ChattingConstant {
    // 웹소켓 URL
    val URL = "ws://101.101.219.23:8080/ws/chat/websocket"

    // 채팅 메세지 타입
    val CHAT_BOT_NOTICE_MESSAGE = "BOT_NOTICE"
    val CHAT_BOT_MESSAGE = "BOT_MESSAGE"
    val PROFILE_MESSAGE = "PROFILE"
    val TALK_MESSAGE = "TALK"

    // 실시간 업데이트 타입
    val REAL_TIME_PENDING = "PENDING"
    val REAL_TIME_APPLIED = "APPLIED"
    val REAL_TIME_APPROVED = "APPROVED"
    val REAL_TIME_DISAPPROVED = "DISAPPROVED"
    val REAL_TIME_MESSAGE_READ = "MESSAGE_READ"
    val REAL_TIME_USER_EXITED = "USER_EXITED"

    // 대화 메세지 그룹핑 상태
    val IS_NOT_SAME_TIME_MESSAGE = 0
    val IS_SAME_TIME_HEADER_MESSAGE = 1
    val IS_SAME_TIME_BODY_MESSAGE = 2
    val IS_SAME_TIME_FOOTER_MESSAGE = 3
}