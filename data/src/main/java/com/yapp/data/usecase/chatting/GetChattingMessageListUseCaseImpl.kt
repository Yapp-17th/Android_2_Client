package com.yapp.data.usecase.chatting

import com.yapp.data.remote.RemoteDataSource
import com.yapp.data.responseToDto
import com.yapp.domain.dto.chatting.ChattingMessageListDto
import com.yapp.domain.usecase.chatting.GetChattingMessageListUseCase
import io.reactivex.Single

class GetChattingMessageListUseCaseImpl(private val remoteDataSource: RemoteDataSource) : GetChattingMessageListUseCase{
    override fun execute(chatRoomId: Long): Single<ChattingMessageListDto> =
        remoteDataSource.getChattingMessageList(chatRoomId).flatMap {
            it.responseToDto()
        }
}