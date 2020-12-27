package com.yapp.sport_planet.remote


import com.beust.klaxon.JsonObject
import com.yapp.sport_planet.data.request.EvaluateReportRequest
import com.yapp.sport_planet.data.request.MyViewEditRequest
import com.yapp.sport_planet.data.request.board.BookMarkRequest
import com.yapp.sport_planet.data.request.board.PostBoardIdRequest
import com.yapp.sport_planet.data.request.board.PostBoardRequest
import com.yapp.sport_planet.data.request.board.ReportRequest
import com.yapp.sport_planet.data.response.OtherHistoryResponse
import com.yapp.sport_planet.data.response.ServerCallBackResponse
import com.yapp.sport_planet.data.response.basic.ExerciseResponse
import com.yapp.sport_planet.data.response.basic.RegionResponse
import com.yapp.sport_planet.data.response.board.BoardContentResponse
import com.yapp.sport_planet.data.response.board.BoardListResponse
import com.yapp.sport_planet.data.response.chatting.ChattingMessageListResponse
import com.yapp.sport_planet.data.response.chatting.ChattingRoomListResponse
import com.yapp.sport_planet.data.response.chatting.CommonServerResponse
import com.yapp.sport_planet.data.response.chatting.MakeChattingRoomResponse
import com.yapp.sport_planet.data.response.common.CommonResponse
import com.yapp.sport_planet.data.response.common.UserTagResponse
import com.yapp.sport_planet.data.response.login.LoginResponse
import com.yapp.sport_planet.data.response.login.SignUpResponse
import com.yapp.sport_planet.data.response.mypage.*
import com.yapp.sport_planet.remote.NetworkHelper.api
import io.reactivex.Single
import retrofit2.Response

class RemoteDataSourceImpl : RemoteDataSource {

    override fun getExercise(): Single<ExerciseResponse> = api.getExercise()

    override fun getRegion(): Single<RegionResponse> = api.getRegion()

    override fun getUserTag(): Single<UserTagResponse> = api.getUserTag()

    override fun postSignIn(userInfo: LoginResponse): Single<Response<ServerCallBackResponse>> =
        api.postSignIn(userInfo)

    override fun autoLogin(): Single<Response<ServerCallBackResponse>> {
        return api.autoLogin()
    }

    override fun postSignUp(userSignUp: SignUpResponse): Single<Response<ServerCallBackResponse>> =
        api.postSignUp(userSignUp)

    override fun deleteUser(): Single<ServerCallBackResponse> = api.deleteUser()

    override fun getMyProfile(): Single<HistoryResponse> = api.getMyProfile()

    override fun getMyProfileEdit(): Single<MyProfileEditResponse> = api.getMyProfileEdit()

    override fun putMyProfile(myViewEditRequest: MyViewEditRequest): Single<ServerCallBackResponse> =
        api.putMyProfile(myViewEditRequest)

    override fun getViewHistory(userId: Long): Single<HistoryResponse> = api.getViewHistory(userId)

    override fun getMyViewHistory(type: String): Single<MyViewHistoryResponse> =
        api.getMyViewHistory(type)

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

    override fun makeChattingRoom(param: JsonObject): Single<MakeChattingRoomResponse> =
        api.makeChattingRoom(param)

    override fun getChattingRoomList(): Single<ChattingRoomListResponse> = api.getChattingRoomList()

    override fun leaveChattingRoom(chatRoomId: Long): Single<CommonServerResponse> =
        api.leaveChattingRoom(chatRoomId)

    override fun getChattingMessageList(chatRoomId: Long): Single<ChattingMessageListResponse> =
        api.getChattingMessageList(chatRoomId)

    override fun applyBoard(boardId: Long, param: JsonObject): Single<CommonServerResponse> =
        api.applyBoard(boardId, param)

    override fun approveBoard(boardId: Long, param: JsonObject): Single<CommonServerResponse> =
        api.approveBoard(boardId, param)

    override fun disapproveBoard(boardId: Long, param: JsonObject): Single<CommonServerResponse> =
        api.disapproveBoard(boardId, param)

    override fun postBoard(
        title: String,
        content: String,
        category: Long,
        city: Long,
        userTag: Long,
        recruitNumber: Int,
        date: String,
        place: String
    ): Single<CommonResponse> {
        return api.postBoard(
            PostBoardRequest(
                title,
                content,
                category,
                city,
                userTag,
                recruitNumber,
                date,
                place
            )
        )
    }

    override fun getBoardList(
        category: String,
        address: String,
        sorting: String
    ): Single<BoardListResponse> {
        return api.getBoardList(
            category = category,
            address = address,
            sorting = sorting
        )
    }

    override fun getBoardContent(boardId: Long): Single<BoardContentResponse> {
        return api.getBoardContent(boardId)
    }

    override fun deleteBoard(boardId: Long): Single<CommonResponse> {
        return api.deleteBoard(boardId)
    }

    override fun editBoard(
        boardId: Long,
        title: String,
        content: String,
        category: Long,
        city: Long,
        userTag: Long,
        recruitNumber: Int,
        date: String,
        place: String
    ): Single<BoardContentResponse> {
        return api.editBoard(
            boardId,
            PostBoardRequest(
                title,
                content,
                category,
                city,
                userTag,
                recruitNumber,
                date,
                place
            )
        )
    }

    override fun createBookMark(boardId: Long): Single<CommonResponse> {
        return api.createBookMark(
            BookMarkRequest((boardId))
        )
    }

    override fun deleteBookMark(boardId: Long): Single<CommonResponse> {
        return api.deleteBookMark(boardId)
    }

    override fun hideBoard(boardId: Long): Single<CommonResponse> {
        return api.hideBoard(
            PostBoardIdRequest(boardId)
        )
    }

    override fun reportBoard(
        reportRequest: ReportRequest
    ): Single<CommonResponse> {
        return api.reportBoard(reportRequest)
    }

    override fun getSearchList(keyword: String): Single<BoardListResponse> {
        return api.search(keyword)
    }
}