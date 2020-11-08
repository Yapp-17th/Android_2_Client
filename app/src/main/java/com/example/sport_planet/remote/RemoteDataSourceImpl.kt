package com.example.sport_planet.remote

import com.beust.klaxon.JsonObject
import com.example.sport_planet.model.*
//import com.example.sport_planet.model.LoginResponse
//import com.example.sport_planet.model.ServerCallBackResponse
import com.example.sport_planet.remote.NetworkHelper.api
import io.reactivex.Single

class RemoteDataSourceImpl : RemoteDataSource{
    override fun getExercise(): Single<ExerciseResponse> = api.getExercise()

    override fun getRegion(): Single<RegionResponse> = api.getRegion()

    //override fun postSignIn(userInfo: LoginResponse): Single<ServerCallBackResponse> = api.postSignIn(userInfo)

    override fun makeChattingRoom(param: JsonObject): Single<MakeChattingRoomResponse> = api.makeChattingRoom(param)

    override fun getChattingRoomList(): Single<ChattingRoomListResponse> = api.getChattingRoomList()

    override fun getChattingMessageList(chatRoomId: Long): Single<ChattingMessageListResponse> = api.getChattingMessageList(chatRoomId)

    override fun makeChattingMessageRead(chatRoomId: Long, messageId: Long): Single<MakeChattingMessageReadResponse> = api.makeChattingMessageRead(chatRoomId, messageId)

    override fun applyBoard(boardId: Long, param: JsonObject): Single<ApplyBoardResponse> = api.applyBoard(boardId, param)

    override fun approveBoard(boardId: Long, param: JsonObject): Single<ApplyBoardResponse> = api.approveBoard(boardId, param)
}