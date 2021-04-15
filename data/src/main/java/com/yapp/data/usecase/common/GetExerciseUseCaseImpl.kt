package com.yapp.data.usecase.common

import com.yapp.data.remote.RemoteDataSource
import com.yapp.data.responseToDto
import com.yapp.domain.dto.basic.ExerciseDto
import com.yapp.domain.usecase.common.GetExerciseUseCase
import io.reactivex.Single

class GetExerciseUseCaseImpl(private val remoteDataSource: RemoteDataSource) : GetExerciseUseCase{
    override fun execute(): Single<List<ExerciseDto>> =
        remoteDataSource.getExercise().flatMap {
            it.responseToDto()
        }
}