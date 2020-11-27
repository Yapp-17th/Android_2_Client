package com.example.sport_planet.remote


import com.example.sport_planet.data.request.EvaluateReportRequest
import com.example.sport_planet.data.request.MyViewEditRequest
import com.example.sport_planet.data.response.*
import io.reactivex.Single
import retrofit2.http.*
import com.beust.klaxon.JsonObject
import com.example.sport_planet.data.response.basic.ExerciseResponse
import com.example.sport_planet.data.response.basic.RegionResponse
import com.example.sport_planet.data.response.login.LoginResponse
import com.example.sport_planet.data.response.login.SignUpResponse
import com.example.sport_planet.data.response.mypage.*
import com.example.sport_planet.model.ChattingMessageListResponse
import com.example.sport_planet.model.ChattingRoomListResponse
import com.example.sport_planet.model.MakeChattingMessageReadResponse
import com.example.sport_planet.model.MakeChattingRoomResponse

interface Api {
    //   로그인 API
    @GET("base-service/v1/exercise")
    fun getExercise(): Single<ExerciseResponse>

    @GET("base-service/v1/address/city")
    fun getRegion(): Single<RegionResponse>

    @POST("login-service/v1/user/sign-in")
    fun postSignIn(@Body userInfo: LoginResponse): Single<ServerCallBackResponse>

    @POST("login-service/v1/user/sign-up")
    fun postSignUp(@Body userSignUp: SignUpResponse): Single<ServerCallBackResponse>

    @DELETE("login-service/v1/user/withdraw")
    fun deleteUser() : Single<ServerCallBackResponse>

    // 마이페이지 API
    @GET("mypage-service/v1/user/my-profile")
    fun getMyProfile(): Single<HistoryResponse>

    @PUT("mypage-service/v1/user/my-profile")
    fun putMyProfile(@Body myViewEditRequest: MyViewEditRequest): Single<ServerCallBackResponse>

    @GET("mypage-service/v1/user/{userId}/profile")
    fun getViewHistory(
        @Path("userId") userId: Long
    ): Single<HistoryResponse>

    @GET("mypage-service/v1/user/my-profile/history")
    fun getMyViewHistory(
        @Query("type") type: String
    ): Single<MyViewHistoryResponse>

    @GET("mypage-service/v1/user/{userId}/profile/history")
    fun getOthersHistory(
        @Path("userId") userId: Long
    ): Single<OtherHistoryResponse>

    @GET("mypage-service/v1/user/my-profile/bookmark")
    fun getBookMarks(): Single<MyBookMarksResponse>

    @GET("mypage-service/v1/user/my-profile/history/{boardId}/applied")
    fun getApplyList(@Path("boardId") boardId: Long): Single<ApplyListResponse>

    @GET("mypage-service/v1/user/my-profile/history/{boardId}/evaluate")
    fun getEvaluateList(@Path("boardId") boardId: Long): Single<EvaluateListResponse>

    @PUT("mypage-service/v1/user/my-profile/history/{boardId}/evaluate/{userId}")
    fun putEvaluateIsLike(
        @Path("boardId") boardId: Long,
        @Path("userId") userId: Long,
        @Query("isLike") isLike: Boolean
    ): Single<ServerCallBackResponse>

    @POST("mypage-service/v1/user/my-profile/history/evaluate/report")
    fun postEvaluateReport(@Body evaluateReportRequest: EvaluateReportRequest): Single<ServerCallBackResponse>


    // 채팅 API
    @POST("chatting-service/v1/chat/room")
    fun makeChattingRoom(
        @Body param: JsonObject
    ): Single<MakeChattingRoomResponse>

    @GET("chatting-service/v1/chat/room")
    fun getChattingRoomList(): Single<ChattingRoomListResponse>

    @HTTP(method = "DELETE", path = "chatting-service/v1/chat/room/{chatRoomId}")
    fun leaveChattingRoom(
        @Path("chatRoomId") chatRoomId: Long
    ): Single<CommonServerResponse>

    @GET("chatting-service/v1/chat/room/{chatRoomId}/message")
    fun getChattingMessageList(
        @Path("chatRoomId") chatRoomId: Long
    ): Single<ChattingMessageListResponse>

    @PUT("chatting-service/v1/chat/room/{chatRoomId}/message/{messageId}")
    fun makeChattingMessageRead(
        @Path("chatRoomId") boardId: Long,
        @Path("messageId") messageId: Long
    ): Single<MakeChattingMessageReadResponse>

    // 게시글 신청, 승인, 승인취소 API
    @POST("chatting-service/v1/board/{boardId}/apply")
    fun applyBoard(
        @Path("boardId") boardId: Long,
        @Body param: JsonObject
    ): Single<CommonServerResponse>

    @POST("chatting-service/v1/board/{boardId}/approve")
    fun approveBoard(
        @Path("boardId") boardId: Long,
        @Body param: JsonObject
    ): Single<CommonServerResponse>

    @HTTP(method = "DELETE", path = "chatting-service/v1/board/{boardId}/approve", hasBody = true)
    fun disapproveBoard(
        @Path("boardId") boardId: Long,
        @Body param: JsonObject
    ): Single<CommonServerResponse>

}