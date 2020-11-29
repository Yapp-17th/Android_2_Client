package com.example.sport_planet.remote

import com.example.sport_planet.data.request.board.BookMarkRequest
import com.example.sport_planet.data.request.board.PostBoardIdRequest
import com.example.sport_planet.data.request.board.PostBoardRequest
import com.example.sport_planet.data.request.board.ReportRequest
import com.example.sport_planet.data.response.board.BoardContentResponse
import com.example.sport_planet.data.response.board.BoardListResponse
import com.example.sport_planet.data.response.common.CommonResponse
import com.example.sport_planet.model.enums.TimeFilterEnum
import io.reactivex.Single
import retrofit2.http.*

interface Api {
    @POST("/v1/board")
    fun postBoard(
        @Body body: PostBoardRequest
    ): Single<CommonResponse>

    @GET("/v1/board")
    fun getBoardList(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20,
        @Query("sorting") sorting: String = TimeFilterEnum.TIME_LATEST.text
    ): Single<BoardListResponse>

    @GET("/v1/board")
    fun getBoardList(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20,
        @Query("sorting") sorting: String = TimeFilterEnum.TIME_LATEST.text,
        @Query(encoded = true, value = "category") category: String,
        @Query(encoded = true, value = "city") city: String
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
    ): Single<BoardContentResponse>

    @POST("/v1/board/bookmark")
    fun createBookMark(
        @Body body: BookMarkRequest
    ): Single<CommonResponse>

    @DELETE("/v1/board/{boardId}/bookmark")
    fun deleteBookMark(
        @Path("boardId") boardId: Long
    ): Single<CommonResponse>

    @POST("/v1/board/hidden")
    fun hideBoard(@Body body: PostBoardIdRequest): Single<CommonResponse>

    @POST("/v1/board/report")
    fun reportBoard(@Body body: ReportRequest): Single<CommonResponse>
}