package com.yapp.data.usecase.login

import com.yapp.data.dtoToResponse
import com.yapp.data.remote.RemoteDataSource
import com.yapp.data.responseToDto
import com.yapp.domain.dto.ServerCallBackDto
import com.yapp.domain.dto.login.SignUpDto
import com.yapp.domain.usecase.login.PostSignUpUseCase
import io.reactivex.Single

class PostSignUpUseCaseImpl(private val remoteDataSource: RemoteDataSource) : PostSignUpUseCase {
    override fun execute(userSignUp: SignUpDto): Single<ServerCallBackDto> =
        remoteDataSource.postSignUp(userSignUp.dtoToResponse()).flatMap {
            it.body()?.responseToDto()
        }
}