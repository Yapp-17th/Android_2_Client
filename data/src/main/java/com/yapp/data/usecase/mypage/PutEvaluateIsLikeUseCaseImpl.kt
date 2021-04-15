package com.yapp.data.usecase.mypage

import com.yapp.data.remote.RemoteDataSource
import com.yapp.data.responseToDto
import com.yapp.domain.dto.ServerCallBackDto
import com.yapp.domain.usecase.mypage.PutEvaluateIsLikeUseCase
import io.reactivex.Single

class PutEvaluateIsLikeUseCaseImpl(private val remoteDataSource: RemoteDataSource) : PutEvaluateIsLikeUseCase{
    override fun execute(boardId: Long, userId: Long, isLike: Boolean): Single<ServerCallBackDto> =
        remoteDataSource.putEvaluateIsLike(boardId, userId, isLike).flatMap {
            it.responseToDto()
        }
}