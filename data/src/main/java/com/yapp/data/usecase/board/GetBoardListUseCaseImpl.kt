package com.yapp.data.usecase.board

import com.yapp.data.remote.RemoteDataSource
import com.yapp.data.responseToDto
import com.yapp.domain.dto.board.BoardDto
import com.yapp.domain.usecase.board.GetBoardListUseCase
import io.reactivex.Single

class GetBoardListUseCaseImpl(private val remoteDataSource: RemoteDataSource) :
    GetBoardListUseCase {
    override fun execute(
        category: String,
        address: String,
        sorting: String
    ): Single<List<BoardDto>> =
    remoteDataSource.getBoardList(category, address, sorting).flatMap {
        it.responseToDto()
    }
}