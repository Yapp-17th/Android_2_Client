package com.example.sport_planet.remote

import com.example.sport_planet.data.request.EvaluateReportRequest
import com.example.sport_planet.data.request.MyViewEditRequest
import com.example.sport_planet.data.response.*
import io.reactivex.Single
import retrofit2.http.*

interface Api {
    //   로그인 API
    @GET("/v1/exercise")
    fun getExercise(): Single<ExerciseResponse>

    @GET("/v1/address/city")
    fun getRegion(): Single<RegionResponse>

    @POST("/v1/user/sign-in")
    fun postSignIn(@Body userInfo: LoginResponse): Single<ServerCallBackResponse>

    @POST("/v1/user/sign-up")
    fun postSignUp(@Body userSignUp: SignUpResponse): Single<ServerCallBackResponse>

    // 마이페이지 API
    @GET("/v1/user/my-profile")
    fun getMyProfile(): Single<ServerCallBackResponse>

    @PUT("/v1/user/my-profile")
    fun putMyProfile(@Body myViewEditRequest: MyViewEditRequest): Single<ServerCallBackResponse>

    @GET("/v1/user/{userId}/profile")
    fun getViewHistory(
        @Path("userId") userId: Long
    ): Single<HistoryResponse>

    @GET("/v1/user/my-profile/history")
    fun getMyViewHistory(
        @Query("type") type: String = "continue"
    ): Single<MyViewHistoryResponse>

    @GET("/v1/user/{userId}/profile/history")
    fun getOthersHistory(
        @Path("userId") userId: Long
    ): Single<OtherHistoryResponse>

    @GET("/v1/user/my-profile/bookmark")
    fun getBookMarks(): Single<MyBookMarksResponse>

    @GET("/v1/user/my-profile/history/{boardId}/applied")
    fun getApplyList(@Path("boardId") boardId: Long): Single<ApplyListResponse>

    @GET("/v1/user/my-profile/history/{boardId}/evaluate")
    fun getEvaluateList(@Path("boardId") boardId: Long): Single<EvaluateListResponse>

    @PUT("/v1/user/my-profile/history/{boardId}/evaluate/{userId}?isLike=true")
    fun putEvaluateIsLike(
        @Path("boardId") boardId: Long,
        @Path("userId") userId: Long,
        @Query("isLike") isLike: Boolean
    ): Single<ServerCallBackResponse>

    @POST("/v1/user/my-profile/history/evaluate/report")
    fun postEvaluateReport(@Body evaluateReportRequest: EvaluateReportRequest): Single<ServerCallBackResponse>


}