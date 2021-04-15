package com.yapp.sport_planet.data.vo.chatting


data class MakeChattingMessageReadVo (

    var transactionTime: String,
    var status: Int,
    var responseType: String,
    var message: String,
    var data: ChattingMessageVo

)