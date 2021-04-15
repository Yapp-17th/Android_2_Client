package com.yapp.domain.usecase.board

import com.beust.klaxon.JsonObject
import com.yapp.domain.dto.chatting.CommonServerDto
import io.reactivex.Single

interface ApplyBoardUseCase {
    fun execute(boardId: Long, param: JsonObject): Single<CommonServerDto>
}