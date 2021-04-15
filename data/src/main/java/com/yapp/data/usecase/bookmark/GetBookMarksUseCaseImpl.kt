package com.yapp.data.usecase.bookmark

import com.yapp.data.remote.RemoteDataSource
import com.yapp.data.responseToDto
import com.yapp.domain.dto.mypage.MyBookMarksDto
import com.yapp.domain.usecase.bookmark.GetBookMarksUseCase
import io.reactivex.Single

class GetBookMarksUseCaseImpl(private val remoteDataSource: RemoteDataSource) : GetBookMarksUseCase{
    override fun execute(): Single<List<MyBookMarksDto>> =
        remoteDataSource.getBookMarks().flatMap {
            it.responseToDto()
        }
}