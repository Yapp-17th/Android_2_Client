package com.yapp.data.usecase.board

import com.yapp.data.remote.RemoteDataSource
import com.yapp.data.responseToDto
import com.yapp.domain.dto.board.BoardDto
import com.yapp.domain.usecase.board.GetSearchListUseCase
import io.reactivex.Single

class GetSearchListUseCaseImpl(private val remoteDataSource: RemoteDataSource) : GetSearchListUseCase {
    override fun getSearchList(keyword: String): Single<List<BoardDto>> =
        remoteDataSource.getSearchList(keyword).flatMap {
            it.responseToDto()
        }
}