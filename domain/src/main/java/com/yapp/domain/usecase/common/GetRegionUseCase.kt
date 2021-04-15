package com.yapp.domain.usecase.common

import com.yapp.domain.dto.basic.RegionDto
import io.reactivex.Single

interface GetRegionUseCase {
    fun execute(): Single<List<RegionDto>>
}