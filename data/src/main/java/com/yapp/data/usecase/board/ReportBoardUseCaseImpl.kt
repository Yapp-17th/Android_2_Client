package com.yapp.data.usecase.board

import com.yapp.data.remote.RemoteDataSource
import com.yapp.data.requestDtoToRequest
import com.yapp.data.responseToDto
import com.yapp.domain.dto.ReportRequestDto
import com.yapp.domain.dto.common.CommonDto
import com.yapp.domain.usecase.board.ReportBoardUseCase
import io.reactivex.Single

class ReportBoardUseCaseImpl(private val remoteDataSource: RemoteDataSource) : ReportBoardUseCase {
    override fun execute(reportRequest: ReportRequestDto): Single<CommonDto> =
        remoteDataSource.reportBoard(reportRequest.requestDtoToRequest()).flatMap {
            it.responseToDto()
        }
}