package com.example.sport_planet.remote


import com.example.sport_planet.data.request.EvaluateReportRequest
import com.example.sport_planet.data.request.MyViewEditRequest
import com.example.sport_planet.data.response.*
import io.reactivex.Single
import retrofit2.http.*
import com.example.sport_planet.model.*
import com.beust.klaxon.JsonObject
const val jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ0ZXN0ZXIxIiwiaWF0IjoxNjA0NjY3MzA5LCJleHAiOjE2MzYyMDMzMTEsImF1ZCI6IiIsInN1YiI6InRlc3RlcjFAZ21haWwuY29tIiwidXNlcklkIjoiMSJ9.Bmbhc-I1r-L-dW5vUzvB9jRsPPKtcqYXutAyWKqkPrc"
//const val jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ0ZXN0ZXIyIiwiaWF0IjoxNjA0NjY3MzA5LCJleHAiOjE2MzYyMDMzMTEsImF1ZCI6IiIsInN1YiI6InRlc3RlcjJAZ21haWwuY29tIiwidXNlcklkIjoiMiJ9.iMoSPe9k5Uj6w8qa4eaOIQqjIzI5Scuts3tBbQ4p79g"

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
        @Query("type") type: String = "continue"
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

    @PUT("mypage-service/v1/user/my-profile/history/{boardId}/evaluate/{userId}?isLike=true")
    fun putEvaluateIsLike(
        @Path("boardId") boardId: Long,
        @Path("userId") userId: Long,
        @Query("isLike") isLike: Boolean
    ): Single<ServerCallBackResponse>

    @POST("mypage-service/v1/user/my-profile/history/evaluate/report")
    fun postEvaluateReport(@Body evaluateReportRequest: EvaluateReportRequest): Single<ServerCallBackResponse>



    @Headers("Authorization: Bearer $jwt")
    @POST("/v1/chat/room")
    fun makeChattingRoom(
        @Body param: JsonObject
    ): Single<MakeChattingRoomResponse>

    @Headers("Authorization: Bearer $jwt")
    @GET("/v1/chat/room")
    fun getChattingRoomList(): Single<ChattingRoomListResponse>

    @Headers("Authorization: Bearer $jwt")
    @GET("/v1/chat/room/{chatRoomId}/message")
    fun getChattingMessageList(
        @Path("chatRoomId") chatRoomId: Long
    ): Single<ChattingMessageListResponse>

    @Headers("Authorization: Bearer $jwt")
    @PUT("/v1/chat/room/{chatRoomId}/message/{messageId}")
    fun makeChattingMessageRead(
        @Path("chatRoomId") boardId: Long,
        @Path("messageId") messageId: Long
    ): Single<MakeChattingMessageReadResponse>

    @Headers("Authorization: Bearer $jwt")
    @POST("/v1/board/{boardId}/apply")
    fun applyBoard(
        @Path("boardId") boardId: Long,
        @Body param: JsonObject
    ): Single<ApplyBoardResponse>

    @Headers("Authorization: Bearer $jwt")
    @POST("/v1/board/{boardId}/approve")
    fun approveBoard(
        @Path("boardId") boardId: Long,
        @Body param: JsonObject
    ): Single<ApplyBoardResponse>

    @Headers("Authorization: Bearer $jwt")
    @HTTP(method = "DELETE", path = "/v1/board/{boardId}/approve", hasBody = true)
    fun disapproveBoard(
        @Path("boardId") boardId: Long,
        @Body param: JsonObject
    ): Single<ApplyBoardResponse>


}