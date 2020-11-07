package com.example.sport_planet.remote

//import com.example.sport_planet.model.LoginResponse
//import com.example.sport_planet.model.ServerCallBackResponse
import com.example.sport_planet.model.request.BookMarkRequest
import com.example.sport_planet.model.request.PostBoardIdRequest
import com.example.sport_planet.model.request.PostBoardRequest
import com.example.sport_planet.model.request.ReportRequest
import com.example.sport_planet.model.response.*
import com.example.sport_planet.remote.NetworkHelper.api
import io.reactivex.Single
import java.util.*

class RemoteDataSourceImpl : RemoteDataSource {
    override fun getExercise(): Single<ExerciseResponse> = api.getExercise()

    override fun getRegion(): Single<RegionResponse> = api.getRegion()

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
        return api.postBoard(
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

    override fun getBoardList(
        page: Int,
        sorting: String,
        category: Long,
        city: Long
    ): Single<BoardListResponse> {
        return api.getBoardList(
            page,
            sorting,
            category,
            city
        )
    }

    override fun getBoardContent(boardId: Long): Single<BoardContentResponse> {
        return api.getBoardContent(boardId)
    }

    override fun deleteBoard(boardId: Long): Single<CommonResponse> {
        return api.deleteBoard(boardId)
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
        return api.editBoard(
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
        return api.createBookMark(
            BookMarkRequest((boardId))
        )
    }

    override fun deleteBookMark(boardId: Long): Single<CommonResponse> {
        return api.deleteBookMark(boardId)
    }

    override fun hideBoard(boardId: Long): Single<CommonResponse> {
        return api.hideBoard(
            PostBoardIdRequest(boardId)
        )
    }

    override fun reportBoard(
        boardId: Long,
        reportType: Long,
        content: String
    ): Single<CommonResponse> {
        return api.reportBoard(
            ReportRequest(boardId, reportType, content)
        )
    }
}