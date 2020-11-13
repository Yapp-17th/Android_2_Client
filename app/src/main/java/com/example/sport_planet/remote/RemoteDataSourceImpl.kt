package com.example.sport_planet.remote

import com.example.sport_planet.data.response.*
import com.example.sport_planet.remote.NetworkHelper.api
import com.example.sport_planet.remote.NetworkHelper.api2
import io.reactivex.Single

class RemoteDataSourceImpl : RemoteDataSource{
    override fun getExercise(): Single<ExerciseResponse> = api2.getExercise()

    override fun getRegion(): Single<RegionResponse> = api2.getRegion()

    override fun postSignIn(userInfo: LoginResponse): Single<ServerCallBackResponse> = api.postSignIn(userInfo)

    override fun postSignUp(userSignUp: SignUpResponse): Single<ServerCallBackResponse> = api.postSignUp(userSignUp)
}