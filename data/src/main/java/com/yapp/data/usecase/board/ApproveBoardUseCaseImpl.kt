package com.yapp.data.usecase.board

import com.beust.klaxon.JsonObject
import com.yapp.data.remote.RemoteDataSource
import com.yapp.data.responseToDto
import com.yapp.domain.dto.chatting.CommonServerDto
import com.yapp.domain.usecase.board.ApproveBoardUseCase
import io.reactivex.Single

class ApproveBoardUseCaseImpl(private val remoteDataSource: RemoteDataSource) :
    ApproveBoardUseCase {
    override fun execute(boardId: Long, param: JsonObject): Single<CommonServerDto> =
        remoteDataSource.approveBoard(boardId, param).flatMap {
            it.responseToDto()
        }
}