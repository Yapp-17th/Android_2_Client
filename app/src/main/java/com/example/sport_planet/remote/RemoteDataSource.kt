package com.example.sport_planet.remote

import com.example.sport_planet.model.*
import com.beust.klaxon.JsonObject
import io.reactivex.Single

interface RemoteDataSource{
    fun getExercise() : Single<ExerciseResponse>

    fun getRegion() : Single<RegionResponse>

    fun postSignIn(userInfo: LoginResponse): Single<ServerCallBackResponse>

    fun postSignUp(userSignUp: SignUpResponse): Single<ServerCallBackResponse>

    fun makeChattingRoom(param: JsonObject) : Single<MakeChattingRoomResponse>

    fun getChattingRoomList() : Single<ChattingRoomListResponse>

    fun getChattingMessageList(chatRoomId: Long) : Single<ChattingMessageListResponse>

    fun makeChattingMessageRead(chatRoomId: Long, messageId: Long) : Single<MakeChattingMessageReadResponse>

    fun applyBoard(boardId:Long, param: JsonObject) : Single<ApplyBoardResponse>

    fun approveBoard(boardId:Long, param: JsonObject) : Single<ApplyBoardResponse>

    fun disapproveBoard(boardId:Long, param: JsonObject) : Single<ApplyBoardResponse>
}