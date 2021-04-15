package com.yapp.domain.usecase.bookmark

import com.yapp.domain.dto.mypage.MyBookMarksDto
import io.reactivex.Single

interface GetBookMarksUseCase {
    fun execute(): Single<List<MyBookMarksDto>>
}