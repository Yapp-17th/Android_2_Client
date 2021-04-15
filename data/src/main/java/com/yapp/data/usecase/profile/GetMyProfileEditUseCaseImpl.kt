package com.yapp.data.usecase.profile

import com.yapp.data.remote.RemoteDataSource
import com.yapp.data.responseToDto
import com.yapp.domain.dto.mypage.MyProfileEditDto
import com.yapp.domain.usecase.profile.GetMyProfileEditUseCase
import io.reactivex.Single

class GetMyProfileEditUseCaseImpl(private val remoteDataSource: RemoteDataSource) : GetMyProfileEditUseCase{
    override fun execute(): Single<MyProfileEditDto> =
        remoteDataSource.getMyProfileEdit().flatMap {
            it.responseToDto()
        }
}