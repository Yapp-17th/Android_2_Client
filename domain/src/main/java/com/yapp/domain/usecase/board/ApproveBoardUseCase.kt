package com.yapp.domain.usecase.board

import com.beust.klaxon.JsonObject
import com.yapp.domain.dto.chatting.CommonServerDto
import io.reactivex.Single

interface ApproveBoardUseCase {
    fun execute(boardId: Long, param: JsonObject): Single<CommonServerDto>
}