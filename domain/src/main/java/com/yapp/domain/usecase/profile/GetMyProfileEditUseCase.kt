package com.yapp.domain.usecase.profile

import com.yapp.domain.dto.mypage.MyProfileEditDto
import io.reactivex.Single

interface GetMyProfileEditUseCase {
    fun execute(): Single<MyProfileEditDto>
}