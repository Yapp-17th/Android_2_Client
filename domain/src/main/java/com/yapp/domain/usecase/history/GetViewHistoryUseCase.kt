package com.yapp.domain.usecase.history

import com.yapp.domain.dto.mypage.HistoryDto
import io.reactivex.Single

interface GetViewHistoryUseCase {
    fun execute(userId: Long): Single<HistoryDto>

}