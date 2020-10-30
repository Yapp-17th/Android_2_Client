package com.example.sport_planet.remote

import com.beust.klaxon.Json
import com.beust.klaxon.JsonObject
import com.example.sport_planet.model.*
//import com.example.sport_planet.model.LoginResponse
//import com.example.sport_planet.model.ServerCallBackResponse
import io.reactivex.Single

interface RemoteDataSource{
    fun getExercise() : Single<ExerciseResponse>

    fun getRegion() : Single<RegionResponse>

    fun makeChattingRoom(param: JsonObject) : Single<MakeChattingRoomResponse>

    fun getChattingRoomList() : Single<ChattingRoomListResponse>

    fun getChattingMessageList(chatRoomId: Int) : Single<ChattingMessageListResponse>

    //fun postSignIn(userInfo: LoginResponse): Single<ServerCallBackResponse>
}