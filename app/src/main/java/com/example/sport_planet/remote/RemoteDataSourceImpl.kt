package com.example.sport_planet.remote

import com.example.sport_planet.data.request.board.BookMarkRequest
import com.example.sport_planet.data.request.board.PostBoardIdRequest
import com.example.sport_planet.data.request.board.PostBoardRequest
import com.example.sport_planet.data.request.board.ReportRequest
import com.example.sport_planet.data.response.board.BoardContentResponse
import com.example.sport_planet.data.response.board.BoardListResponse
import com.example.sport_planet.data.response.common.AddressCityResponse
import com.example.sport_planet.data.response.common.CommonResponse
import com.example.sport_planet.data.response.common.ExerciseResponse
import com.example.sport_planet.remote.NetworkHelper.api
import io.reactivex.Single
import java.util.*

class RemoteDataSourceImpl : RemoteDataSource {
    override fun getAddressCity(): Single<AddressCityResponse> {
        return api.getAddressCity()
    }

    override fun getExercise(): Single<ExerciseResponse> {
        return api.getExercise()
    }

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
        size: Int,
        page: Int,
        sorting: String,
        category: String,
        address: String
    ): Single<BoardListResponse> {
        return api.getBoardList(
            size = size,
            page = page,
            sorting = sorting,
            category = category,
            address = address
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