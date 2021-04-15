package com.yapp.data.usecase.profile

import com.yapp.data.remote.RemoteDataSource
import com.yapp.data.responseToDto
import com.yapp.domain.dto.mypage.HistoryDto
import com.yapp.domain.usecase.profile.GetMyProfileUseCase
import io.reactivex.Single

class GetMyProfileUseCaseImpl(private val remoteDataSource: RemoteDataSource) : GetMyProfileUseCase{
    override fun execute(): Single<HistoryDto> =
        remoteDataSource.getMyProfile().flatMap {
            it.responseToDto()
        }
}