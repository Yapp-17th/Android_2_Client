package com.example.sport_planet.remote

import com.example.sport_planet.data.request.board.BookMarkRequest
import com.example.sport_planet.data.request.board.PostBoardIdRequest
import com.example.sport_planet.data.request.board.PostBoardRequest
import com.example.sport_planet.data.request.board.ReportRequest
import com.example.sport_planet.data.response.board.BoardContentResponse
import com.example.sport_planet.data.response.board.BoardListResponse
import com.example.sport_planet.data.response.common.CommonResponse
import com.example.sport_planet.data.enums.TimeFilterEnum
import com.example.sport_planet.data.response.common.AddressCityResponse
import com.example.sport_planet.data.response.common.ExerciseResponse
import com.example.sport_planet.data.response.common.UserTagResponse
import io.reactivex.Single
import retrofit2.http.*

interface Api {
    //common
    @GET("/api/base-service/v1/address/city")
    fun getAddressCity(): Single<AddressCityResponse>

    @GET("/api/base-service/v1/exercise")
    fun getExercise(): Single<ExerciseResponse>

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
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20,
        @Query("sorting") sorting: String = TimeFilterEnum.TIME_LATEST.query,
        @Query(encoded = true, value = "category") category: String,
        @Query(encoded = true, value = "address") address: String
    ): Single<BoardListResponse>

    @GET("/api/board-service/v1/board/{boardId}")
    fun getBoardContent(
        @Path("boardId") boardId: Long
    ): Single<BoardContentResponse>

    @DELETE("/api/board-service/v1/board/{boarId}")
    fun deleteBoard(
        @Path("boardId") boardId: Long
    ): Single<CommonResponse>

    @PUT("/api/board-service/v1/board/{boardId")
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
}