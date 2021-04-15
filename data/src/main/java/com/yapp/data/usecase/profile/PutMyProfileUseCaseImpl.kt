package com.yapp.data.usecase.profile

import com.yapp.data.dtoToRequest
import com.yapp.data.remote.RemoteDataSource
import com.yapp.data.responseToDto
import com.yapp.domain.dto.MyViewEditRequestDto
import com.yapp.domain.dto.ServerCallBackDto
import com.yapp.domain.usecase.profile.PutMyProfileUseCase
import io.reactivex.Single

class PutMyProfileUseCaseImpl(private val remoteDataSource: RemoteDataSource) : PutMyProfileUseCase{
    override fun execute(myViewEditRequestDto: MyViewEditRequestDto): Single<ServerCallBackDto> =
        remoteDataSource.putMyProfile(myViewEditRequestDto.dtoToRequest()).flatMap {
            it.responseToDto()
        }
}