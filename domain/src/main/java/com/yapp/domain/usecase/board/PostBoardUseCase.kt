package com.yapp.domain.usecase.board

import com.yapp.domain.dto.common.CommonDto
import io.reactivex.Single

interface PostBoardUseCase {
    fun execute(
        title: String,
        content: String,
        category: Long,
        city: Long,
        userTag: Long,
        recruitNumber: Int,
        date: String,
        place: String
    ): Single<CommonDto>
}