package com.yapp.domain.usecase.mypage

import com.yapp.domain.dto.mypage.EvaluateDto
import io.reactivex.Single

interface GetEvaluateListUseCase {
    fun execute(boardId: Long): Single<List<EvaluateDto>>
}