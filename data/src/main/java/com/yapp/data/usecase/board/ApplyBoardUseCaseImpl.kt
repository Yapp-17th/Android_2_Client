package com.yapp.data.usecase.board

import com.beust.klaxon.JsonObject
import com.yapp.data.remote.RemoteDataSource
import com.yapp.data.responseToDto
import com.yapp.domain.dto.chatting.CommonServerDto
import com.yapp.domain.usecase.board.ApplyBoardUseCase
import io.reactivex.Single

class ApplyBoardUseCaseImpl(private val remoteDataSource: RemoteDataSource) : ApplyBoardUseCase {
    override fun execute(boardId: Long, param: JsonObject): Single<CommonServerDto> =
        remoteDataSource.applyBoard(boardId, param).flatMap {
            it.responseToDto()
        }
}