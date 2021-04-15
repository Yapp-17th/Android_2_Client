package com.yapp.domain.usecase.bookmark

import com.yapp.domain.dto.common.CommonDto
import io.reactivex.Single

interface DeleteBookMarkUseCase {
     fun execute(boardId: Long): Single<CommonDto>
}