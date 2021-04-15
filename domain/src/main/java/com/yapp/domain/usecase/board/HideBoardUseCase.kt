package com.yapp.domain.usecase.board

import com.yapp.domain.dto.common.CommonDto
import io.reactivex.Single

interface HideBoardUseCase {
    fun execute(boardId: Long): Single<CommonDto>
}