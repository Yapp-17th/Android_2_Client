package com.yapp.data.usecase.bookmark

import com.yapp.data.remote.RemoteDataSource
import com.yapp.data.responseToDto
import com.yapp.domain.dto.common.CommonDto
import com.yapp.domain.usecase.bookmark.DeleteBookMarkUseCase
import io.reactivex.Single

class DeleteBookMarkUseCaseImpl(private val remoteDataSource: RemoteDataSource) : DeleteBookMarkUseCase {
    override fun execute(boardId: Long): Single<CommonDto> =
        remoteDataSource.deleteBookMark(boardId).flatMap {
            it.responseToDto()
        }
}