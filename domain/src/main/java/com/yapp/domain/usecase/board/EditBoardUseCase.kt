package com.yapp.domain.usecase.board

import com.yapp.domain.dto.board.BoardContentDto
import io.reactivex.Single

interface EditBoardUseCase {
    fun execute(
       boardId: Long,
       title: String,
       content: String,
       category: Long,
       city: Long,
       userTag: Long,
       recruitNumber: Int,
       date: String,
       place: String
    ): Single<BoardContentDto>
}