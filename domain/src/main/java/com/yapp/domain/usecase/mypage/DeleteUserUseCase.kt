package com.yapp.domain.usecase.mypage

import com.yapp.domain.dto.ServerCallBackDto
import io.reactivex.Single

interface DeleteUserUseCase {
    fun execute(): Single<ServerCallBackDto>
}