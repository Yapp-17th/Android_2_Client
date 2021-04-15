package com.yapp.data.usecase.chatting

import com.yapp.data.remote.RemoteDataSource
import com.yapp.data.responseToDto
import com.yapp.domain.dto.chatting.ChattingRoomListDto
import com.yapp.domain.usecase.chatting.GetChattingRoomListUseCase
import io.reactivex.Single

class GetChattingRoomListUseCaseImpl(private val remoteDataSource: RemoteDataSource) : GetChattingRoomListUseCase{
    override fun getChattingRoomList(): Single<List<ChattingRoomListDto>> =
        remoteDataSource.getChattingRoomList().flatMap {
            it.responseToDto()
        }
}