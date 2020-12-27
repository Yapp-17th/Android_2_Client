package com.yapp.sport_planet.remote


import com.beust.klaxon.JsonObject
import com.yapp.sport_planet.data.enums.TimeFilterEnum
import com.yapp.sport_planet.data.request.EvaluateReportRequest
import com.yapp.sport_planet.data.request.MyViewEditRequest
import com.yapp.sport_planet.data.request.board.BookMarkRequest
import com.yapp.sport_planet.data.request.board.PostBoardIdRequest
import com.yapp.sport_planet.data.request.board.PostBoardRequest
import com.yapp.sport_planet.data.request.board.ReportRequest
import com.yapp.sport_planet.data.response.OtherHistoryResponse
import com.yapp.sport_planet.data.response.ServerCallBackResponse
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
import retrofit2.http.*

interface Api {
    //   로그인 API
    @GET("base-service/v1/exercise")
    fun getExercise(): Single<com.yapp.sport_planet.data.response.basic.ExerciseResponse>

    @GET("base-service/v1/address/city")
    fun getRegion(): Single<RegionResponse>

    @POST("login-service/v1/user/sign-in")
    fun postSignIn(@Body userInfo: LoginResponse): Single<retrofit2.Response<ServerCallBackResponse>>

    @GET("login-service/v1/user/auto-in")
    fun autoLogin(): Single<retrofit2.Response<ServerCallBackResponse>>

    @POST("login-service/v1/user/sign-up")
    fun postSignUp(@Body userSignUp: SignUpResponse): Single<retrofit2.Response<ServerCallBackResponse>>

    @DELETE("login-service/v1/user/withdraw")
    fun deleteUser(): Single<ServerCallBackResponse>

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

    @GET("mypage-service/v1/user/my-profile/edit")
    fun getMyProfileEdit(): Single<MyProfileEditResponse>

    /** 채팅 API Start **/
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
    /** 채팅 API End **/

    /** 게시글 신청, 승인, 승인취소 API Start **/
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

    /** 게시글 신청, 승인, 승인취소 API End **/

    //common
    @GET("/api/base-service//v1/user/tag")
    fun getUserTag(): Single<UserTagResponse>
    //common

    //board
    @POST("/api/board-service/v1/board")
    fun postBoard(
        @Body body: PostBoardRequest
    ): Single<CommonResponse>

    @GET("/api/board-service/v1/board")
    fun getBoardList(
        @Query(encoded = true, value = "category") category: String,
        @Query(encoded = true, value = "address") address: String,
        @Query("sorting") sorting: String = TimeFilterEnum.TIME_LATEST.query
    ): Single<BoardListResponse>

    @GET("/api/board-service/v1/board/{boardId}")
    fun getBoardContent(
        @Path("boardId") boardId: Long
    ): Single<BoardContentResponse>

    @DELETE("/api/board-service/v1/board/{boardId}")
    fun deleteBoard(
        @Path("boardId") boardId: Long
    ): Single<CommonResponse>

    @PUT("/api/board-service/v1/board/{boardId}")
    fun editBoard(
        @Path("boardId") boardId: Long,
        @Body body: PostBoardRequest
    ): Single<BoardContentResponse>

    @POST("/api/board-service/v1/board/bookmark")
    fun createBookMark(
        @Body body: BookMarkRequest
    ): Single<CommonResponse>

    @DELETE("/api/board-service/v1/board/{boardId}/bookmark")
    fun deleteBookMark(
        @Path("boardId") boardId: Long
    ): Single<CommonResponse>

    @POST("/api/board-service/v1/board/hidden")
    fun hideBoard(@Body body: PostBoardIdRequest): Single<CommonResponse>

    @POST("/api/board-service/v1/board/report")
    fun reportBoard(@Body body: ReportRequest): Single<CommonResponse>
    //board

    //search
    @GET("/api/board-service/v1/board/search")
    fun search(
        @Query(encoded = true, value = "keyword") keyword: String
    ): Single<BoardListResponse>
    //search
}