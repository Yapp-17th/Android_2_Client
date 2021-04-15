package com.yapp.data.usecase.mypage

import com.yapp.data.dtoToRequest
import com.yapp.data.remote.RemoteDataSource
import com.yapp.data.responseToDto
import com.yapp.domain.dto.EvaluateReportRequestDto
import com.yapp.domain.dto.ServerCallBackDto
import com.yapp.domain.usecase.mypage.PostEvaluateReportUseCase
import io.reactivex.Single

class PostEvaluateReportUseCaseImpl(private val remoteDataSource: RemoteDataSource) : PostEvaluateReportUseCase{
    override fun execute(evaluateReportRequestDto: EvaluateReportRequestDto): Single<ServerCallBackDto> =
        remoteDataSource.postEvaluateReport(evaluateReportRequestDto.dtoToRequest()).flatMap {
            it.responseToDto()
        }
}