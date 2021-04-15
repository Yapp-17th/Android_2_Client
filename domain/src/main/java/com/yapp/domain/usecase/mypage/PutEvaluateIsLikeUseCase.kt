package com.yapp.domain.usecase.mypage

import com.yapp.domain.dto.ServerCallBackDto
import io.reactivex.Single

interface PutEvaluateIsLikeUseCase {
    fun execute(
        boardId: Long,
        userId: Long,
        isLike: Boolean
    ): Single<ServerCallBackDto>
}