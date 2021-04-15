package com.yapp.data.usecase.board

import com.yapp.data.remote.RemoteDataSource
import com.yapp.data.responseToDto
import com.yapp.domain.dto.common.CommonDto
import com.yapp.domain.usecase.board.HideBoardUseCase
import io.reactivex.Single

class HideBoardUseCaseImpl(private val remoteDataSource: RemoteDataSource) : HideBoardUseCase {
    override fun execute(boardId: Long): Single<CommonDto> =
        remoteDataSource.hideBoard(boardId).flatMap {
            it.responseToDto()
        }
}