package com.yapp.domain.usecase.mypage

import com.yapp.domain.dto.common.UserTagDto
import io.reactivex.Single

interface GetUserTagUseCase {
    fun execute(): Single<List<UserTagDto>>
}