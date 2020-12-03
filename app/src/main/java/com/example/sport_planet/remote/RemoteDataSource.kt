package com.example.sport_planet.remote

import com.beust.klaxon.JsonObject
import com.example.sport_planet.data.request.EvaluateReportRequest
import com.example.sport_planet.data.request.MyViewEditRequest
import com.example.sport_planet.data.response.OtherHistoryResponse
import com.example.sport_planet.data.response.ServerCallBackResponse
import com.example.sport_planet.data.response.basic.ExerciseResponse
import com.example.sport_planet.data.response.basic.RegionResponse
import com.example.sport_planet.data.response.board.BoardContentResponse
import com.example.sport_planet.data.response.board.BoardListResponse
import com.example.sport_planet.data.response.chatting.*
import com.example.sport_planet.data.response.common.CommonResponse
import com.example.sport_planet.data.response.login.LoginResponse
import com.example.sport_planet.data.response.login.SignUpResponse
import com.example.sport_planet.data.response.mypage.*
import io.reactivex.Single
import java.util.*

interface RemoteDataSource {

    fun postSignIn(userInfo: LoginResponse): Single<ServerCallBackResponse>

    fun postSignUp(userSignUp: SignUpResponse): Single<ServerCallBackResponse>

    fun deleteUser() : Single<ServerCallBackResponse>

    fun getMyProfile(): Single<HistoryResponse>

    fun putMyProfile(myViewEditRequest: MyViewEditRequest): Single<ServerCallBackResponse>

    fun getViewHistory(userId: Long): Single<HistoryResponse>

    fun getMyViewHistory(type : String): Single<MyViewHistoryResponse>

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

    /** Common Api **/
    fun getExercise(): Single<ExerciseResponse>

    fun getRegion(): Single<RegionResponse>
    /** Common Api **/

    /** Board Api Start **/
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
        size: Int,
        page: Int,
        sorting: String,
        category: String,
        address: String
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
    /** Board Api End **/

}