package com.yapp.data.usecase.login

import com.yapp.data.remote.RemoteDataSource
import com.yapp.data.responseToDto
import com.yapp.domain.dto.ServerCallBackDto
import com.yapp.domain.usecase.login.AutoLoginUseCase
import io.reactivex.Single

class AutoLoginUseCaseImpl(private val remoteDataSource: RemoteDataSource) : AutoLoginUseCase {
    override fun execute(): Single<ServerCallBackDto> =
        remoteDataSource.autoLogin().flatMap {
            it.body()?.responseToDto()
        }
}