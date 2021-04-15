package com.yapp.data.usecase.mypage

import com.yapp.data.remote.RemoteDataSource
import com.yapp.data.responseToDto
import com.yapp.domain.dto.mypage.EvaluateDto
import com.yapp.domain.usecase.mypage.GetEvaluateListUseCase
import io.reactivex.Single

class GetEvaluateListUseCaseImpl(private val remoteDataSource: RemoteDataSource) : GetEvaluateListUseCase{
    override fun execute(boardId: Long): Single<List<EvaluateDto>> =
        remoteDataSource.getEvaluateList(boardId).flatMap {
            it.responseToDto()
        }
}