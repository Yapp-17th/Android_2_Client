package com.example.sport_planet.remote

import com.example.sport_planet.model.*
import io.reactivex.Single
import retrofit2.http.Body

interface RemoteDataSource{
    fun getExercise() : Single<ExerciseResponse>

    fun getRegion() : Single<RegionResponse>

    fun postSignIn(userInfo: LoginResponse): Single<ServerCallBackResponse>

    fun postSignUp(userSignUp: SignUpResponse): Single<ServerCallBackResponse>
}