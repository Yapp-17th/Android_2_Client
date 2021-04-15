package com.yapp.data.usecase.chatting

import com.beust.klaxon.JsonObject
import com.yapp.data.remote.RemoteDataSource
import com.yapp.data.responseToDto
import com.yapp.domain.dto.chatting.MakeChattingRoomDto
import com.yapp.domain.usecase.chatting.MakeChattingRoomUseCase
import io.reactivex.Single

class MakeChattingRoomUseCaseImpl(private val remoteDataSource: RemoteDataSource) : MakeChattingRoomUseCase{
    override fun makeChattingRoom(param: JsonObject): Single<MakeChattingRoomDto> =
        remoteDataSource.makeChattingRoom(param).flatMap {
            it.responseToDto()
        }
}