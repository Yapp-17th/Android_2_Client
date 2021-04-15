package com.yapp.domain.usecase.board

import com.yapp.domain.dto.mypage.ApplyListDto
import io.reactivex.Single

interface GetApplyListUseCase {
     fun execute(boardId: Long): Single<List<ApplyListDto>>

}