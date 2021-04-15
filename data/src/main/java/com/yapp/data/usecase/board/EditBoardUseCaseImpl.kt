package com.yapp.data.usecase.board

import com.yapp.data.remote.RemoteDataSource
import com.yapp.data.responseToDto
import com.yapp.domain.dto.board.BoardContentDto
import com.yapp.domain.usecase.board.EditBoardUseCase
import io.reactivex.Single

class EditBoardUseCaseImpl(private val remoteDataSource: RemoteDataSource) : EditBoardUseCase {
    override fun execute(
        boardId: Long,
        title: String,
        content: String,
        category: Long,
        city: Long,
        userTag: Long,
        recruitNumber: Int,
        date: String,
        place: String
    ): Single<BoardContentDto> =
        remoteDataSource.editBoard(
            boardId,
            title,
            content,
            category,
            city,
            userTag,
            recruitNumber,
            date,
            place
        ).flatMap {
            it.responseToDto()
        }
}