package com.yapp.domain.usecase.profile

import com.yapp.domain.dto.MyViewEditRequestDto
import com.yapp.domain.dto.ServerCallBackDto
import io.reactivex.Single

interface PutMyProfileUseCase {
    fun execute(myViewEditRequestDto: MyViewEditRequestDto): Single<ServerCallBackDto>
}