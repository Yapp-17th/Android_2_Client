package com.yapp.data.usecase.board

import com.beust.klaxon.JsonObject
import com.yapp.data.remote.RemoteDataSource
import com.yapp.data.responseToDto
import com.yapp.domain.dto.chatting.CommonServerDto
import com.yapp.domain.usecase.board.DisapproveBoardUseCase
import io.reactivex.Single

class DisapproveBoardUseCaseImpl(private val remoteDataSource: RemoteDataSource) : DisapproveBoardUseCase {
    override fun execute(boardId: Long, param: JsonObject): Single<CommonServerDto> =
        remoteDataSource.disapproveBoard(boardId,param).flatMap {
            it.responseToDto()
        }
}