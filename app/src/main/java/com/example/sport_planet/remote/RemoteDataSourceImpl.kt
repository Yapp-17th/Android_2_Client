package com.example.sport_planet.remote


import com.example.sport_planet.data.request.EvaluateReportRequest
import com.example.sport_planet.data.request.MyViewEditRequest
import com.example.sport_planet.data.response.*
import com.example.sport_planet.model.*
import com.beust.klaxon.JsonObject
import com.example.sport_planet.remote.NetworkHelper.api
import com.example.sport_planet.remote.NetworkHelper.api2
import com.example.sport_planet.remote.NetworkHelper.api3
import io.reactivex.Single

class RemoteDataSourceImpl : RemoteDataSource {
    override fun getExercise(): Single<ExerciseResponse> = api2.getExercise()

    override fun getRegion(): Single<RegionResponse> = api2.getRegion()

    override fun postSignIn(userInfo: LoginResponse): Single<ServerCallBackResponse> =
        api.postSignIn(userInfo)


    override fun postSignUp(userSignUp: SignUpResponse): Single<ServerCallBackResponse> =
        api.postSignUp(userSignUp)

    override fun getMyProfile(): Single<HistoryResponse> = api3.getMyProfile()

    override fun putMyProfile(myViewEditRequest: MyViewEditRequest): Single<ServerCallBackResponse> =
        api3.putMyProfile(myViewEditRequest)

    override fun getViewHistory(userId: Long): Single<HistoryResponse> = api3.getViewHistory(userId)

    override fun getMyViewHistory(): Single<MyViewHistoryResponse> = api3.getMyViewHistory()

    override fun getOthersHistory(userId: Long): Single<OtherHistoryResponse> =
        api3.getOthersHistory(userId)

    override fun getBookMarks(): Single<MyBookMarksResponse> = api3.getBookMarks()

    override fun getApplyList(boardId: Long): Single<ApplyListResponse> = api3.getApplyList(boardId)

    override fun getEvaluateList(boardId: Long): Single<EvaluateListResponse> =
        api3.getEvaluateList(boardId)

    override fun putEvaluateIsLike(
        boardId: Long,
        userId: Long,
        isLike: Boolean
    ): Single<ServerCallBackResponse> =
        api3.putEvaluateIsLike(boardId = boardId, userId = userId, isLike = isLike)

    override fun postEvaluateReport(evaluateReportRequest: EvaluateReportRequest): Single<ServerCallBackResponse> =
        api3.postEvaluateReport(evaluateReportRequest)

    override fun makeChattingRoom(param: JsonObject): Single<MakeChattingRoomResponse> = api.makeChattingRoom(param)

    override fun getChattingRoomList(): Single<ChattingRoomListResponse> = api.getChattingRoomList()

    override fun getChattingMessageList(chatRoomId: Long): Single<ChattingMessageListResponse> = api.getChattingMessageList(chatRoomId)

    override fun makeChattingMessageRead(chatRoomId: Long, messageId: Long): Single<MakeChattingMessageReadResponse> = api.makeChattingMessageRead(chatRoomId, messageId)

    override fun applyBoard(boardId: Long, param: JsonObject): Single<ApplyBoardResponse> = api.applyBoard(boardId, param)

    override fun approveBoard(boardId: Long, param: JsonObject): Single<ApplyBoardResponse> = api.approveBoard(boardId, param)

    override fun disapproveBoard(boardId: Long, param: JsonObject): Single<ApplyBoardResponse> = api.disapproveBoard(boardId, param)

}