package com.yapp.data.usecase.bookmark

import com.yapp.data.remote.RemoteDataSource
import com.yapp.data.responseToDto
import com.yapp.domain.dto.common.CommonDto
import com.yapp.domain.usecase.bookmark.CreateBookMarkUseCase
import io.reactivex.Single

class CreateBookMarkUseCaseImpl(private val remoteDataSource: RemoteDataSource) : CreateBookMarkUseCase{
    override fun execute(boardId: Long): Single<CommonDto> =
        remoteDataSource.createBookMark(boardId).flatMap {
            it.responseToDto()
        }
}