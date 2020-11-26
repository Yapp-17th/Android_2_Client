package com.example.sport_planet.remote


import com.example.sport_planet.data.request.EvaluateReportRequest
import com.example.sport_planet.data.request.MyViewEditRequest
import com.example.sport_planet.data.response.*

import com.beust.klaxon.JsonObject

import io.reactivex.Single

interface RemoteDataSource {
    fun getExercise(): Single<ExerciseResponse>

    fun getRegion(): Single<RegionResponse>

    fun postSignIn(userInfo: LoginResponse): Single<ServerCallBackResponse>

    fun postSignUp(userSignUp: SignUpResponse): Single<ServerCallBackResponse>

    fun deleteUser() : Single<ServerCallBackResponse>

    fun getMyProfile(): Single<HistoryResponse>

    fun putMyProfile(myViewEditRequest: MyViewEditRequest): Single<ServerCallBackResponse>

    fun getViewHistory(userId: Long): Single<HistoryResponse>

    fun getMyViewHistory(): Single<MyViewHistoryResponse>

    fun getOthersHistory(userId: Long): Single<OtherHistoryResponse>

    fun getBookMarks(): Single<MyBookMarksResponse>

    fun getApplyList(boardId: Long): Single<ApplyListResponse>

    fun getEvaluateList(boardId: Long): Single<EvaluateListResponse>

    fun putEvaluateIsLike(
        boardId: Long,
        userId: Long,
        isLike: Boolean
    ): Single<ServerCallBackResponse>

    fun postEvaluateReport(evaluateReportRequest: EvaluateReportRequest): Single<ServerCallBackResponse>

    fun makeChattingRoom(param: JsonObject) : Single<MakeChattingRoomResponse>

    fun getChattingRoomList() : Single<ChattingRoomListResponse>

    fun leaveChattingRoom(chatRoomId: Long) : Single<CommonServerResponse>

    fun getChattingMessageList(chatRoomId: Long) : Single<ChattingMessageListResponse>

    fun makeChattingMessageRead(chatRoomId: Long, messageId: Long) : Single<MakeChattingMessageReadResponse>

    fun applyBoard(boardId:Long, param: JsonObject) : Single<CommonServerResponse>

    fun approveBoard(boardId:Long, param: JsonObject) : Single<CommonServerResponse>

    fun disapproveBoard(boardId:Long, param: JsonObject) : Single<CommonServerResponse>

}