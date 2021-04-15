package com.yapp.domain.usecase.chatting

import com.yapp.domain.dto.chatting.ChattingMessageListDto
import io.reactivex.Single

interface GetChattingMessageListUseCase {
     fun execute(chatRoomId: Long): Single<ChattingMessageListDto>
}