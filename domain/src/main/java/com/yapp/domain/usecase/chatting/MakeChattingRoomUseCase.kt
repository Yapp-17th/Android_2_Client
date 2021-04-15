package com.yapp.domain.usecase.chatting

import com.beust.klaxon.JsonObject
import com.yapp.domain.dto.chatting.MakeChattingRoomDto
import io.reactivex.Single

interface MakeChattingRoomUseCase {
    fun execute(param: JsonObject): Single<MakeChattingRoomDto>
}