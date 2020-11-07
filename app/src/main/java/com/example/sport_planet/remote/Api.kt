package com.example.sport_planet.remote

//import com.example.sport_planet.model.LoginResponse
//import com.example.sport_planet.model.ServerCallBackResponse
import com.example.sport_planet.model.request.BookMarkRequest
import com.example.sport_planet.model.request.PostBoardRequest
import com.example.sport_planet.model.request.ReportRequest
import com.example.sport_planet.model.response.*
import com.example.sport_planet.util.Constants
import io.reactivex.Single
import retrofit2.http.*

interface Api {
    @GET("/v1/exercise")
    fun getExercise(): Single<ExerciseResponse>

    @GET("/v1/address/city")
    fun getRegion(): Single<RegionResponse>

//    @POST("/user/sign-in")
//    fun postSignIn(@Body userInfo: LoginResponse): Single<ServerCallBackResponse>

    @POST("/v1/board")
    fun postBoard(
        @Body body: PostBoardRequest
    ): Single<CommonResponse>

    @GET("/v1/board")
    fun getBoardList(
        @Query("page") page: Int = 0,
        @Query("sorting") sorting: String = Constants.TIME_LATEST,
        @Query("category") category: Long,
        @Query("city") city: Long
    ): Single<BoardListResponse>

    @GET("v1/board/{boardId}")
    fun getBoardContent(
        @Path("boardId") boardId: Long
    ): Single<BoardContentResponse>

    @DELETE("/v1/board/{boarId}")
    fun deleteBoard(
        @Path("boardId") boardId: Long
    ): Single<CommonResponse>

    @PUT("/v1/board/{boardId")
    fun editBoard(
        @Path("boardId") boardId: Long,
        @Body body: PostBoardRequest
    ): Single<CommonResponse>

    @POST("/v1/board/bookmark")
    fun createBookMark(
        @Body body: BookMarkRequest
    ): Single<CommonResponse>

    @DELETE("/v1/board/{boardId}/bookmark")
    fun deleteBookMark(
        @Path("boardId") boardId: Long
    ): Single<CommonResponse>

    @POST("/v1/board/hidden")
    fun hideBoard(@Body body: PostBoardRequest): Single<CommonResponse>

    @POST("/v1/board/report")
    fun reportBoard(@Body body: ReportRequest): Single<CommonResponse>
}