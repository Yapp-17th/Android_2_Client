package com.yapp.domain.usecase.board

import com.yapp.domain.dto.board.BoardDto
import io.reactivex.Single

interface GetSearchListUseCase {
    fun getSearchList(keyword: String): Single<List<BoardDto>>
}