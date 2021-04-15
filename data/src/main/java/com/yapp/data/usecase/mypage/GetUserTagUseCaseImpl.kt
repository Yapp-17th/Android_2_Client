package com.yapp.data.usecase.mypage

import com.yapp.data.remote.RemoteDataSource
import com.yapp.data.responseToDto
import com.yapp.domain.dto.common.UserTagDto
import com.yapp.domain.usecase.mypage.GetUserTagUseCase
import io.reactivex.Single

class GetUserTagUseCaseImpl(private val remoteDataSource: RemoteDataSource) : GetUserTagUseCase{
    override fun execute(): Single<List<UserTagDto>> =
        remoteDataSource.getUserTag().flatMap {
            it.responseToDto()
        }
}