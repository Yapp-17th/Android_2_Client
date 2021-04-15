package com.yapp.domain.usecase.board

import com.yapp.domain.dto.board.BoardContentDto
import io.reactivex.Single

interface GetBoardContentUseCase {
     fun execute(boardId: Long): Single<BoardContentDto>
}