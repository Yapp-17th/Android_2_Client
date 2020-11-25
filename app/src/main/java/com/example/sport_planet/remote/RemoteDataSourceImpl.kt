package com.example.sport_planet.remote

import com.beust.klaxon.JsonObject
import com.example.sport_planet.data.request.EvaluateReportRequest
import com.example.sport_planet.data.request.MyViewEditRequest
import com.example.sport_planet.data.request.board.BookMarkRequest
import com.example.sport_planet.data.request.board.PostBoardIdRequest
import com.example.sport_planet.data.request.board.PostBoardRequest
import com.example.sport_planet.data.request.board.ReportRequest
import com.example.sport_planet.data.response.*
import com.example.sport_planet.data.response.board.BoardContentResponse
import com.example.sport_planet.data.response.board.BoardListResponse
import com.example.sport_planet.data.response.common.CommonResponse
import com.example.sport_planet.remote.NetworkHelper.api
import com.example.sport_planet.remote.NetworkHelper.api2
import io.reactivex.Single
import java.util.*

class RemoteDataSourceImpl : RemoteDataSource {
    override fun getExercise(): Single<ExerciseResponse> = api2.getExercise()
    override fun getRegion(): Single<RegionResponse> {
        TODO("Not yet implemented")
    }

    override fun getMyProfile(): Single<HistoryResponse> = api.getMyProfile()
    override fun putMyProfile(myViewEditRequest: MyViewEditRequest): Single<ServerCallBackResponse> {
        TODO("Not yet implemented")
    }

    override fun getViewHistory(userId: Long): Single<HistoryResponse> {
        TODO("Not yet implemented")
    }

    override fun getMyViewHistory(): Single<MyViewHistoryResponse> {
        TODO("Not yet implemented")
    }

    override fun getOthersHistory(userId: Long): Single<OtherHistoryResponse> {
        TODO("Not yet implemented")
    }

    override fun getBookMarks(): Single<MyBookMarksResponse> {
        TODO("Not yet implemented")
    }

    override fun getApplyList(boardId: Long): Single<ApplyListResponse> {
        TODO("Not yet implemented")
    }

    override fun getEvaluateList(boardId: Long): Single<EvaluateListResponse> {
        TODO("Not yet implemented")
    }

    override fun putEvaluateIsLike(
        boardId: Long,
        userId: Long,
        isLike: Boolean
    ): Single<ServerCallBackResponse> {
        TODO("Not yet implemented")
    }

    override fun postEvaluateReport(evaluateReportRequest: EvaluateReportRequest): Single<ServerCallBackResponse> {
        TODO("Not yet implemented")
    }

    override fun postSignIn(userInfo: LoginResponse): Single<ServerCallBackResponse> =
        api.postSignIn(userInfo)

    override fun postSignUp(userSignUp: SignUpResponse): Single<ServerCallBackResponse> =
        api.postSignUp(userSignUp)

    override fun deleteUser(): Single<ServerCallBackResponse> {
        TODO("Not yet implemented")
    }

    override fun makeChattingRoom(param: JsonObject): Single<MakeChattingRoomResponse> =
        api.makeChattingRoom(param)

    override fun getChattingRoomList(): Single<ChattingRoomListResponse> = api.getChattingRoomList()

    override fun getChattingMessageList(chatRoomId: Long): Single<ChattingMessageListResponse> =
        api.getChattingMessageList(chatRoomId)

    override fun makeChattingMessageRead(
        chatRoomId: Long,
        messageId: Long
    ): Single<MakeChattingMessageReadResponse> = api.makeChattingMessageRead(chatRoomId, messageId)

    override fun applyBoard(boardId: Long, param: JsonObject): Single<ApplyBoardResponse> =
        api.applyBoard(boardId, param)

    override fun approveBoard(boardId: Long, param: JsonObject): Single<ApplyBoardResponse> {
        TODO("Not yet implemented")
    }

    override fun disapproveBoard(boardId: Long, param: JsonObject): Single<ApplyBoardResponse> {
        TODO("Not yet implemented")
    }

    override fun postBoard(
        title: String,
        content: String,
        category: Long,
        city: Long,
        userTag: Long,
        recruitNumber: Int,
        date: Date,
        place: String
    ): Single<CommonResponse> {
        return api2.postBoard(
            PostBoardRequest(
                title,
                content,
                category,
                city,
                userTag,
                recruitNumber,
                Calendar.getInstance().time,
                place
            )
        )
    }

    override fun getBoardList(size: Int, page: Int, sorting: String): Single<BoardListResponse> {
        return api2.getBoardList(
            size = size,
            page = page,
            sorting = sorting
        )
    }

    override fun getBoardList(
        size: Int,
        page: Int,
        sorting: String,
        category: String,
        city: String
    ): Single<BoardListResponse> {
        return api2.getBoardList(
            size = size,
            page = page,
            sorting = sorting,
            category = category,
            city = city
        )
    }

    override fun getBoardContent(boardId: Long): Single<BoardContentResponse> {
        return api2.getBoardContent(boardId)
    }

    override fun deleteBoard(boardId: Long): Single<CommonResponse> {
        return api2.deleteBoard(boardId)
    }

    override fun editBoard(
        boardId: Long,
        title: String,
        content: String,
        category: Long,
        city: Long,
        userTag: Long,
        recruitNumber: Int,
        date: Date,
        place: String
    ): Single<BoardContentResponse> {
        return api2.editBoard(
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
        return api2.createBookMark(
            BookMarkRequest((boardId))
        )
    }

    override fun deleteBookMark(boardId: Long): Single<CommonResponse> {
        return api2.deleteBookMark(boardId)
    }

    override fun hideBoard(boardId: Long): Single<CommonResponse> {
        return api2.hideBoard(
            PostBoardIdRequest(boardId)
        )
    }

    override fun reportBoard(
        boardId: Long,
        reportType: Long,
        content: String
    ): Single<CommonResponse> {
        return api2.reportBoard(
            ReportRequest(boardId, reportType, content)
        )
    }
}