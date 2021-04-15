package com.yapp.data.usecase.chatting

import com.yapp.data.remote.RemoteDataSource
import com.yapp.data.responseToDto
import com.yapp.domain.dto.chatting.CommonServerDto
import com.yapp.domain.usecase.chatting.LeaveChattingRoomUseCase
import io.reactivex.Single

class LeaveChattingRoomUseCaseImpl(private val remoteDataSource: RemoteDataSource) : LeaveChattingRoomUseCase{
    override fun execute(chatRoomId: Long): Single<CommonServerDto> =
        remoteDataSource.leaveChattingRoom(chatRoomId).flatMap {
            it.responseToDto()
        }
}