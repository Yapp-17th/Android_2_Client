package com.yapp.domain.usecase.login

import com.yapp.domain.dto.ServerCallBackDto
import com.yapp.domain.dto.login.LoginDto
import io.reactivex.Single
import retrofit2.Response

interface PostSignInUseCase {
    fun execute(userInfo: LoginDto): Single<ServerCallBackDto>
}