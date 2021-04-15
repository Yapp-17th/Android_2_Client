package com.yapp.data.usecase.common

import com.yapp.data.remote.RemoteDataSource
import com.yapp.data.responseToDto
import com.yapp.domain.dto.basic.RegionDto
import com.yapp.domain.usecase.common.GetRegionUseCase
import io.reactivex.Single

class GetRegionUseCaseImpl(private val remoteDataSource: RemoteDataSource) : GetRegionUseCase{
    override fun execute(): Single<List<RegionDto>> =
        remoteDataSource.getRegion().flatMap {
            it.responseToDto()
        }
}