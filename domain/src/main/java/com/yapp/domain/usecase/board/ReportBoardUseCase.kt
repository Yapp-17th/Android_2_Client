package com.yapp.domain.usecase.board

import com.yapp.domain.dto.ReportRequestDto
import com.yapp.domain.dto.common.CommonDto
import io.reactivex.Single

interface ReportBoardUseCase {
    fun execute(
        reportRequest: ReportRequestDto
    ): Single<CommonDto>
}