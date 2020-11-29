package com.example.sport_planet.remote

import com.example.sport_planet.data.response.board.BoardContentResponse
import com.example.sport_planet.data.response.board.BoardListResponse
import com.example.sport_planet.data.response.common.CommonResponse
import com.example.sport_planet.model.enums.TimeFilterEnum
import io.reactivex.Single
import java.util.*

interface RemoteDataSource {

    fun postBoard(
        title: String,
        content: String,
        category: Long,
        city: Long,
        userTag: Long,
        recruitNumber: Int,
        date: Date,
        place: String
    ): Single<CommonResponse>

    fun getBoardList(
        size: Int = 20,
        page: Int = 0,
        sorting: String = TimeFilterEnum.TIME_LATEST.text
    ): Single<BoardListResponse>

    fun getBoardList(
        size: Int = 20,
        page: Int = 0,
        sorting: String = TimeFilterEnum.TIME_LATEST.text,
        category: String = "",
        city: String = ""
    ): Single<BoardListResponse>

    fun getBoardContent(
        boardId: Long
    ): Single<BoardContentResponse>

    fun deleteBoard(
        boardId: Long
    ): Single<CommonResponse>

    fun editBoard(
        boardId: Long,
        title: String,
        content: String,
        category: Long,
        city: Long,
        userTag: Long,
        recruitNumber: Int,
        date: Date,
        place: String
    ): Single<BoardContentResponse>

    fun createBookMark(
        boardId: Long
    ): Single<CommonResponse>

    fun deleteBookMark(
        boardId: Long
    ): Single<CommonResponse>

    fun hideBoard(
        boardId: Long
    ): Single<CommonResponse>

    fun reportBoard(
        boardId: Long,
        reportType: Long,
        content: String
    ): Single<CommonResponse>


}