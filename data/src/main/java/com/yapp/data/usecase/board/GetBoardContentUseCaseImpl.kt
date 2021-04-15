package com.yapp.data.usecase.board

import com.yapp.data.remote.RemoteDataSource
import com.yapp.data.responseToDto
import com.yapp.domain.dto.board.BoardContentDto
import com.yapp.domain.usecase.board.GetBoardContentUseCase
import io.reactivex.Single

class GetBoardContentUseCaseImpl(private val remoteDataSource: RemoteDataSource) :
    GetBoardContentUseCase {
    override fun execute(boardId: Long): Single<BoardContentDto> =
        remoteDataSource.getBoardContent(boardId).flatMap {
            it.responseToDto()
        }
}