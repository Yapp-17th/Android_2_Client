package com.yapp.domain.usecase.common

import com.yapp.domain.dto.basic.ExerciseDto
import io.reactivex.Single

interface GetExerciseUseCase {
    fun execute(): Single<List<ExerciseDto>>
}