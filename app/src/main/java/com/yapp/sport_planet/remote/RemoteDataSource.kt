package com.yapp.sport_planet.remote

import com.beust.klaxon.JsonObject
import com.yapp.sport_planet.data.request.EvaluateReportRequest
import com.yapp.sport_planet.data.request.MyViewEditRequest
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
import io.reactivex.Single
import retrofit2.Response

interface RemoteDataSource {

    fun postSignIn(userInfo: LoginResponse): Single<Response<ServerCallBackResponse>>

    fun autoLogin(): Single<Response<ServerCallBackResponse>>

    fun postSignUp(userSignUp: SignUpResponse): Single<Response<ServerCallBackResponse>>

    fun deleteUser(): Single<ServerCallBackResponse>

    fun getMyProfile(): Single<HistoryResponse>

    fun getMyProfileEdit(): Single<MyProfileEditResponse>

    fun putMyProfile(myViewEditRequest: MyViewEditRequest): Single<ServerCallBackResponse>

    fun getViewHistory(userId: Long): Single<HistoryResponse>

    fun getMyViewHistory(type: String): Single<MyViewHistoryResponse>

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

    fun makeChattingRoom(param: JsonObject): Single<MakeChattingRoomResponse>

    fun getChattingRoomList(): Single<ChattingRoomListResponse>

    fun leaveChattingRoom(chatRoomId: Long): Single<CommonServerResponse>

    fun getChattingMessageList(chatRoomId: Long): Single<ChattingMessageListResponse>

    fun applyBoard(boardId: Long, param: JsonObject): Single<CommonServerResponse>

    fun approveBoard(boardId: Long, param: JsonObject): Single<CommonServerResponse>

    fun disapproveBoard(boardId: Long, param: JsonObject): Single<CommonServerResponse>

    /** Common Api **/
    fun getExercise(): Single<ExerciseResponse>

    fun getRegion(): Single<RegionResponse>

    fun getUserTag(): Single<UserTagResponse>
    /** Common Api **/

    /** Board Api Start **/
    fun postBoard(
        title: String,
        content: String,
        category: Long,
        city: Long,
        userTag: Long,
        recruitNumber: Int,
        date: String,
        place: String
    ): Single<CommonResponse>

    fun getBoardList(
        category: String,
        address: String,
        sorting: String
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
        date: String,
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

    fun reportBoard(reportRequest: ReportRequest): Single<CommonResponse>
    /** Board Api End **/

    /** Search Api Start**/
    fun getSearchList(keyword: String): Single<BoardListResponse>
    /** Search Api End**/
}