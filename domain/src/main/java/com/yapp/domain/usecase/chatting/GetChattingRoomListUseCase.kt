package com.yapp.domain.usecase.chatting

import com.yapp.domain.dto.chatting.ChattingRoomListDto
import io.reactivex.Single

interface GetChattingRoomListUseCase {
    fun getChattingRoomList(): Single<List<ChattingRoomListDto>>

}