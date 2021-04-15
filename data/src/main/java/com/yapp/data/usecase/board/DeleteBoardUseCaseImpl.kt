package com.yapp.data.usecase.board

import com.yapp.data.remote.RemoteDataSource
import com.yapp.data.responseToDto
import com.yapp.domain.dto.common.CommonDto
import com.yapp.domain.usecase.board.DeleteBoardUseCase
import io.reactivex.Single

class DeleteBoardUseCaseImpl(private val remoteDataSource: RemoteDataSource) : DeleteBoardUseCase {
    override fun execute(boardId: Long): Single<CommonDto> =
        remoteDataSource.deleteBoard(boardId).flatMap {
            it.responseToDto()
        }
}