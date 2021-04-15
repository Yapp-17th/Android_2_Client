package com.yapp.domain.usecase.mypage

import com.yapp.domain.dto.EvaluateReportRequestDto
import com.yapp.domain.dto.ServerCallBackDto
import io.reactivex.Single

interface PostEvaluateReportUseCase {
    fun execute(evaluateReportRequestDto: EvaluateReportRequestDto): Single<ServerCallBackDto>
}