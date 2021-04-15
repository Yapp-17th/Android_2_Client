package com.yapp.data.usecase.history

import com.yapp.data.remote.RemoteDataSource
import com.yapp.data.responseToDto
import com.yapp.domain.dto.mypage.HistoryDto
import com.yapp.domain.usecase.history.GetViewHistoryUseCase
import io.reactivex.Single

class GetViewHistoryUseCaseImpl(private val remoteDataSource: RemoteDataSource) : GetViewHistoryUseCase{
    override fun execute(userId: Long): Single<HistoryDto> =
        remoteDataSource.getViewHistory(userId).flatMap {
            it.responseToDto()
        }
}