package com.yapp.sport_planet.data.vo.chatting

data class ChattingMessageListVo (
    var transactionTime: String,
    var firstUnreadMessageId: Int,
    var boardTitle: String,
    var appliedStatus: String,
    var data: List<ChattingMessageVo>

)