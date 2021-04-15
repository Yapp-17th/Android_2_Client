package com.yapp.domain.usecase.board

import com.yapp.domain.dto.board.BoardDto
import io.reactivex.Single

interface GetBoardListUseCase {
    fun execute(
        category: String,
        address: String,
        sorting: String
    ): Single<List<BoardDto>>
}