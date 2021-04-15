package com.yapp.domain.usecase.login

import com.yapp.domain.dto.ServerCallBackDto
import com.yapp.domain.dto.login.SignUpDto
import io.reactivex.Single

interface PostSignUpUseCase {
    fun execute(userSignUp: SignUpDto): Single<ServerCallBackDto>
}