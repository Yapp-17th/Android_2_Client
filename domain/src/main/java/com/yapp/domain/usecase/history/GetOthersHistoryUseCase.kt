package com.yapp.domain.usecase.history

import com.yapp.domain.dto.OtherHistoryDto
import io.reactivex.Single

interface GetOthersHistoryUseCase {
    fun execute(userId: Long): Single<List<OtherHistoryDto>>
}