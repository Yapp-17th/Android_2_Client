package com.example.sport_planet.remote


import com.example.sport_planet.data.request.EvaluateReportRequest
import com.example.sport_planet.data.request.MyViewEditRequest
import com.example.sport_planet.data.response.*
import com.beust.klaxon.JsonObject
import com.example.sport_planet.remote.NetworkHelper.api
import io.reactivex.Single

class RemoteDataSourceImpl : RemoteDataSource {
    override fun getExercise(): Single<ExerciseResponse> = api.getExercise()

    override fun getRegion(): Single<RegionResponse> = api.getRegion()

    override fun postSignIn(userInfo: LoginResponse): Single<ServerCallBackResponse> =
        api.postSignIn(userInfo)

    override fun postSignUp(userSignUp: SignUpResponse): Single<ServerCallBackResponse> =
        api.postSignUp(userSignUp)

    override fun deleteUser(): Single<ServerCallBackResponse>  = api.deleteUser()

    override fun getMyProfile(): Single<HistoryResponse> = api.getMyProfile()

    override fun putMyProfile(myViewEditRequest: MyViewEditRequest): Single<ServerCallBackResponse> =
        api.putMyProfile(myViewEditRequest)

    override fun getViewHistory(userId: Long): Single<HistoryResponse> = api.getViewHistory(userId)

    override fun getMyViewHistory(type : String): Single<MyViewHistoryResponse> = api.getMyViewHistory(type)

    override fun getOthersHistory(userId: Long): Single<OtherHistoryResponse> =
        api.getOthersHistory(userId)

    override fun getBookMarks(): Single<MyBookMarksResponse> = api.getBookMarks()

    override fun getApplyList(boardId: Long): Single<ApplyListResponse> = api.getApplyList(boardId)

    override fun getEvaluateList(boardId: Long): Single<EvaluateListResponse> =
        api.getEvaluateList(boardId)

    override fun putEvaluateIsLike(
        boardId: Long,
        userId: Long,
        isLike: Boolean
    ): Single<ServerCallBackResponse> =
        api.putEvaluateIsLike(boardId = boardId, userId = userId, isLike = isLike)

    override fun postEvaluateReport(evaluateReportRequest: EvaluateReportRequest): Single<ServerCallBackResponse> =
        api.postEvaluateReport(evaluateReportRequest)

    override fun makeChattingRoom(param: JsonObject): Single<MakeChattingRoomResponse> = api.makeChattingRoom(param)

    override fun getChattingRoomList(): Single<ChattingRoomListResponse> = api.getChattingRoomList()

    override fun leaveChattingRoom(chatRoomId: Long): Single<CommonServerResponse> = api.leaveChattingRoom(chatRoomId)

    override fun getChattingMessageList(chatRoomId: Long): Single<ChattingMessageListResponse> = api.getChattingMessageList(chatRoomId)

    override fun makeChattingMessageRead(chatRoomId: Long, messageId: Long): Single<MakeChattingMessageReadResponse> = api.makeChattingMessageRead(chatRoomId, messageId)

    override fun applyBoard(boardId: Long, param: JsonObject): Single<CommonServerResponse> = api.applyBoard(boardId, param)

    override fun approveBoard(boardId: Long, param: JsonObject): Single<CommonServerResponse> = api.approveBoard(boardId, param)

    override fun disapproveBoard(boardId: Long, param: JsonObject): Single<CommonServerResponse> = api.disapproveBoard(boardId, param)

}