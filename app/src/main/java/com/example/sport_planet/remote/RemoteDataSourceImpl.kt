package com.example.sport_planet.remote

import com.example.sport_planet.model.*
import com.example.sport_planet.remote.NetworkHelper.api
import com.example.sport_planet.remote.NetworkHelper.api2
import io.reactivex.Single
import retrofit2.http.Body

class RemoteDataSourceImpl : RemoteDataSource{
    override fun getExercise(): Single<ExerciseResponse> = api.getExercise()

    override fun getRegion(): Single<RegionResponse> = api.getRegion()

    override fun postSignIn(userInfo: LoginResponse): Single<ServerCallBackResponse> = api2.postSignIn(userInfo)

    override fun postSignUp(userSignUp: SignUpResponse): Single<ServerCallBackResponse> = api2.postSignUp(userSignUp)
}