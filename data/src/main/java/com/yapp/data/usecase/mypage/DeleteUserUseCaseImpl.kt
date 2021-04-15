package com.yapp.data.usecase.mypage

import com.yapp.data.remote.RemoteDataSource
import com.yapp.data.responseToDto
import com.yapp.domain.dto.ServerCallBackDto
import com.yapp.domain.usecase.mypage.DeleteUserUseCase
import io.reactivex.Single

class DeleteUserUseCaseImpl(private val remoteDataSource: RemoteDataSource) : DeleteUserUseCase {
    override fun execute(): Single<ServerCallBackDto> =
        remoteDataSource.deleteUser().flatMap {
            it.responseToDto()
        }
}