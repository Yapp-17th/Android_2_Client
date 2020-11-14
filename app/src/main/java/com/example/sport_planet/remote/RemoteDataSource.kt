package com.example.sport_planet.remote

import com.example.sport_planet.model.*
import com.example.sport_planet.model.enums.TimeFilterEnum
import com.example.sport_planet.model.response.BoardContentResponse
import com.example.sport_planet.model.response.BoardListResponse
import com.example.sport_planet.model.response.CommonResponse
import com.google.gson.JsonObject
import io.reactivex.Single
import java.util.*

interface RemoteDataSource{
    fun getExercise() : Single<ExerciseResponse>

    fun getRegion() : Single<RegionResponse>

//    fun postSignIn(userInfo: LoginResponse): Single<ServerCallBackResponse>

    fun postBoard(
        title: String,
        content: String,
        category: Long,
        city: Long,
        userTag: Long,
        recruitNumber: Int,
        date: Date,
        place: String
    ): Single<CommonResponse>

    fun getBoardList(
        page: Int = 0,
        sorting: String = TimeFilterEnum.TIME_LATEST.text,
        category: Long = 0,
        city: Long = 0
    ): Single<BoardListResponse>

    fun getBoardContent(
        boardId: Long
    ): Single<BoardContentResponse>

    fun deleteBoard(
        boardId: Long
    ): Single<CommonResponse>

    fun editBoard(
        boardId: Long,
        title: String,
        content: String,
        category: Long,
        city: Long,
        userTag: Long,
        recruitNumber: Int,
        date: Date,
        place: String
    ): Single<BoardContentResponse>

    fun createBookMark(
        boardId: Long
    ): Single<CommonResponse>

    fun deleteBookMark(
        boardId: Long
    ): Single<CommonResponse>

    fun hideBoard(
        boardId: Long
    ): Single<CommonResponse>

    fun reportBoard(
        boardId: Long,
        reportType: Long,
        content: String
    ): Single<CommonResponse>
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