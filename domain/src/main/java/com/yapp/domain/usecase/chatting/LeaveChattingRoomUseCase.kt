package com.yapp.domain.usecase.chatting

import com.yapp.domain.dto.chatting.CommonServerDto
import io.reactivex.Single

interface LeaveChattingRoomUseCase {
    fun execute(chatRoomId: Long): Single<CommonServerDto>
}