package com.example.sport_planet.remote

import com.beust.klaxon.JsonObject
import com.example.sport_planet.model.*
//import com.example.sport_planet.model.LoginResponse
//import com.example.sport_planet.model.ServerCallBackResponse
import io.reactivex.Single
import retrofit2.http.*
const val jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJoYWVtaW4iLCJpYXQiOjE2MDQyMjkzMzYsImV4cCI6MTYzNTc2NTMzNywiYXVkIjoiIiwic3ViIjoiaGFlbWluQGdtYWlsLmNvbSIsInVzZXJJZCI6IjIifQ.cgeVPYmMxc5BoPsoeHgl44p-OrBfe3RwjSD1YekTNxo"

interface Api {

    @GET("/v1/exercise")
    fun getExercise(): Single<ExerciseResponse>

    @GET("/v1/address/city")
    fun getRegion(): Single<RegionResponse>

    //@POST("/user/sign-in")
    //fun postSignIn(@Body userInfo: LoginResponse): Single<ServerCallBackResponse>

    @Headers("Authorization: Bearer $jwt")
    @POST("/v1/chat/room")
    fun makeChattingRoom(
        @Body param: JsonObject
    ): Single<MakeChattingRoomResponse>

    @Headers("Authorization: Bearer $jwt")
    @GET("/v1/chat/room")
    fun getChattingRoomList(): Single<ChattingRoomListResponse>

    @Headers("Authorization: Bearer $jwt")
    @GET("/v1/chat/room/{chatRoomId}/message")
    fun getChattingMessageList(@Path("chatRoomId") chatRoomId: Int): Single<ChattingMessageListResponse>
}