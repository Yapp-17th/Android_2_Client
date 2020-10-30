package com.example.sport_planet.remote

import com.beust.klaxon.Json
import com.beust.klaxon.JsonObject
import com.example.sport_planet.model.*
//import com.example.sport_planet.model.LoginResponse
//import com.example.sport_planet.model.ServerCallBackResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {
    @GET("/v1/exercise")
    fun getExercise(): Single<ExerciseResponse>

    @GET("/v1/address/city")
    fun getRegion(): Single<RegionResponse>

    //@POST("/user/sign-in")
    //fun postSignIn(@Body userInfo: LoginResponse): Single<ServerCallBackResponse>

    @POST("/v1/chat/room")
    fun makeChattingRoom(
        @Body param: JsonObject
    ): Single<MakeChattingRoomResponse>

    @GET("/v1/chat/room")
    fun getChattingRoomList(): Single<ChattingRoomListResponse>

    @GET("/v1/chat/message/{chatRoomId}")
    fun getChattingMessageList(@Path("chatRoomId") chatRoomId: Int): Single<ChattingMessageListResponse>
}