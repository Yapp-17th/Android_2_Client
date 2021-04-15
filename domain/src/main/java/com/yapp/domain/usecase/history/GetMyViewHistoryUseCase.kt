package com.yapp.domain.usecase.history

import com.yapp.domain.dto.mypage.MyViewHistoryDto
import io.reactivex.Single

interface GetMyViewHistoryUseCase {
    fun execute(type: String): Single<List<MyViewHistoryDto>>
}