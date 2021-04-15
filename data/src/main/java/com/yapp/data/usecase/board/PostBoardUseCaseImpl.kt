package com.yapp.data.usecase.board

import com.yapp.data.remote.RemoteDataSource
import com.yapp.data.responseToDto
import com.yapp.domain.dto.common.CommonDto
import com.yapp.domain.usecase.board.PostBoardUseCase
import io.reactivex.Single

class PostBoardUseCaseImpl(private val remoteDataSource: RemoteDataSource) : PostBoardUseCase {
    override fun execute(
        title: String,
        content: String,
        category: Long,
        city: Long,
        userTag: Long,
        recruitNumber: Int,
        date: String,
        place: String
    ): Single<CommonDto> =
        remoteDataSource.postBoard(
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