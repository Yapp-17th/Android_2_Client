package com.yapp.domain.usecase.login

import com.yapp.domain.dto.ServerCallBackDto
import io.reactivex.Single

interface AutoLoginUseCase {
    fun execute(): Single<ServerCallBackDto>
}