package com.yapp.data.usecase.history

import com.yapp.data.remote.RemoteDataSource
import com.yapp.data.responseToDto
import com.yapp.domain.dto.mypage.MyViewHistoryDto
import com.yapp.domain.usecase.history.GetMyViewHistoryUseCase
import io.reactivex.Single

class GetMyViewHistoryUseCaseImpl(private val remoteDataSource: RemoteDataSource) : GetMyViewHistoryUseCase{
    override fun execute(type: String): Single<List<MyViewHistoryDto>> =
        remoteDataSource.getMyViewHistory(type).flatMap {
            it.responseToDto()
        }
}