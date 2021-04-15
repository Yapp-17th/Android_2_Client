package com.yapp.domain.dto.chatting

data class ChattingMessageListDto (
    var transactionTime: String,
    var firstUnreadMessageId: Int,
    var boardTitle: String,
    var appliedStatus: String,
    var data: List<ChattingMessageDto>

)