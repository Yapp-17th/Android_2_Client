package com.example.sport_planet.remote

import com.example.sport_planet.model.ChattingMessageResponse
import com.example.sport_planet.model.ExerciseResponse
//import com.example.sport_planet.model.LoginResponse
import com.example.sport_planet.model.RegionResponse
//import com.example.sport_planet.model.ServerCallBackResponse
import com.example.sport_planet.remote.NetworkHelper.api
import io.reactivex.Single
import retrofit2.http.Body

class RemoteDataSourceImpl : RemoteDataSource{
    override fun getExercise(): Single<ExerciseResponse> = api.getExercise()

    override fun getRegion(): Single<RegionResponse> = api.getRegion()

    //override fun postSignIn(userInfo: LoginResponse): Single<ServerCallBackResponse> = api.postSignIn(userInfo)

    override fun getChattingMessage(): Single<ChattingMessageResponse> = api.getChattingMessage()

}