package com.yapp.data.usecase.history

import com.yapp.data.remote.RemoteDataSource
import com.yapp.data.responseToDto
import com.yapp.domain.dto.OtherHistoryDto
import com.yapp.domain.usecase.history.GetOthersHistoryUseCase
import io.reactivex.Single

class GetOthersHistoryUseCaseImpl(private val remoteDataSource: RemoteDataSource) : GetOthersHistoryUseCase{
    override fun execute(userId: Long): Single<List<OtherHistoryDto>> =
        remoteDataSource.getOthersHistory(userId).flatMap {
            it.responseToDto()
        }
}