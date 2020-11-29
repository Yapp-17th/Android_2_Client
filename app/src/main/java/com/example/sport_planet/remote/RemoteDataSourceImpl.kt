package com.example.sport_planet.remote

import com.example.sport_planet.data.request.board.BookMarkRequest
import com.example.sport_planet.data.request.board.PostBoardIdRequest
import com.example.sport_planet.data.request.board.PostBoardRequest
import com.example.sport_planet.data.request.board.ReportRequest
import com.example.sport_planet.data.response.board.BoardContentResponse
import com.example.sport_planet.data.response.board.BoardListResponse
import com.example.sport_planet.data.response.common.CommonResponse
import com.example.sport_planet.remote.NetworkHelper.api2
import io.reactivex.Single
import java.util.*

class RemoteDataSourceImpl : RemoteDataSource {

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
        return api2.postBoard(
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

    override fun getBoardList(size: Int, page: Int, sorting: String): Single<BoardListResponse> {
        return api2.getBoardList(
            size = size,
            page = page,
            sorting = sorting
        )
    }

    override fun getBoardList(
        size: Int,
        page: Int,
        sorting: String,
        category: String,
        city: String
    ): Single<BoardListResponse> {
        return api2.getBoardList(
            size = size,
            page = page,
            sorting = sorting,
            category = category,
            city = city
        )
    }

    override fun getBoardContent(boardId: Long): Single<BoardContentResponse> {
        return api2.getBoardContent(boardId)
    }

    override fun deleteBoard(boardId: Long): Single<CommonResponse> {
        return api2.deleteBoard(boardId)
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
        return api2.editBoard(
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
        return api2.createBookMark(
            BookMarkRequest((boardId))
        )
    }

    override fun deleteBookMark(boardId: Long): Single<CommonResponse> {
        return api2.deleteBookMark(boardId)
    }

    override fun hideBoard(boardId: Long): Single<CommonResponse> {
        return api2.hideBoard(
            PostBoardIdRequest(boardId)
        )
    }

    override fun reportBoard(
        boardId: Long,
        reportType: Long,
        content: String
    ): Single<CommonResponse> {
        return api2.reportBoard(
            ReportRequest(boardId, reportType, content)
        )
    }
}