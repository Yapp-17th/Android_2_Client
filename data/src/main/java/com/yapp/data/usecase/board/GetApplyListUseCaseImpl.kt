package com.yapp.data.usecase.board

import com.yapp.data.remote.RemoteDataSource
import com.yapp.data.responseToDto
import com.yapp.domain.dto.mypage.ApplyListDto
import com.yapp.domain.usecase.board.GetApplyListUseCase
import io.reactivex.Single

class GetApplyListUseCaseImpl(private val remoteDataSource: RemoteDataSource) : GetApplyListUseCase {
    override fun execute(boardId: Long): Single<List<ApplyListDto>> =
        remoteDataSource.getApplyList(boardId).flatMap {
            it.responseToDto()
        }
}