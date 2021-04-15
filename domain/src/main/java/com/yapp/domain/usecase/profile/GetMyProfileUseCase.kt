package com.yapp.domain.usecase.profile

import com.yapp.domain.dto.mypage.HistoryDto
import io.reactivex.Single

interface GetMyProfileUseCase {
    fun execute(): Single<HistoryDto>
}