package com.yapp.domain.dto.chatting


data class MakeChattingMessageReadDto (

    var transactionTime: String,
    var status: Int,
    var responseType: String,
    var message: String,
    var data: ChattingMessageDto

)