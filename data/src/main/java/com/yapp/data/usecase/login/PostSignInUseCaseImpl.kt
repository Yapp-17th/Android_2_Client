package com.yapp.data.usecase.login

import com.yapp.data.dtoToResponse
import com.yapp.data.remote.RemoteDataSource
import com.yapp.data.responseToDto
import com.yapp.domain.dto.ServerCallBackDto
import com.yapp.domain.dto.login.LoginDto
import com.yapp.domain.usecase.login.PostSignInUseCase
import io.reactivex.Single

class PostSignInUseCaseImpl(private val remoteDataSource: RemoteDataSource) : PostSignInUseCase {
    override fun execute(userInfo: LoginDto): Single<ServerCallBackDto> =
        remoteDataSource.postSignIn(userInfo.dtoToResponse()).flatMap {
            it.body()?.responseToDto()
        }
}