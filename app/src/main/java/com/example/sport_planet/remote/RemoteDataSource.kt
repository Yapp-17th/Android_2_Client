package com.example.sport_planet.remote

import com.example.sport_planet.model.ChattingMessageListResponse
import com.example.sport_planet.model.ChattingRoomListResponse
import com.example.sport_planet.model.ExerciseResponse
//import com.example.sport_planet.model.LoginResponse
import com.example.sport_planet.model.RegionResponse
//import com.example.sport_planet.model.ServerCallBackResponse
import io.reactivex.Single

interface RemoteDataSource{
    fun getExercise() : Single<ExerciseResponse>

    fun getRegion() : Single<RegionResponse>

    fun getChattingRoomList() : Single<ChattingRoomListResponse>

    fun getChattingMessageList() : Single<ChattingMessageListResponse>

    //fun postSignIn(userInfo: LoginResponse): Single<ServerCallBackResponse>
}