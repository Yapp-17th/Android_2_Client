package com.example.sport_planet.remote

import com.example.sport_planet.model.response.*
//import com.example.sport_planet.model.LoginResponse
//import com.example.sport_planet.model.ServerCallBackResponse
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
        TODO("Not yet implemented")
    }

    override fun getBoardList(
        page: Int,
        sorting: String,
        category: Long,
        city: Long
    ): Single<BoardListResponse> {
        TODO("Not yet implemented")
    }

    override fun getBoardContent(boardId: Long): Single<BoardContentResponse> {
        TODO("Not yet implemented")
    }

    override fun deleteBoard(boardId: Long): Single<CommonResponse> {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }

    override fun createBookMark(boardId: Long): Single<CommonResponse> {
        TODO("Not yet implemented")
    }

    override fun deleteBookMark(boardId: Long): Single<CommonResponse> {
        TODO("Not yet implemented")
    }

    override fun hideBoard(boardId: Long): Single<CommonResponse> {
        TODO("Not yet implemented")
    }

    override fun reportBoard(
        boardId: Long,
        reportType: Long,
        content: String
    ): Single<CommonResponse> {
        TODO("Not yet implemented")
    }

//    override fun postSignIn(userInfo: LoginResponse): Single<ServerCallBackResponse> = api.postSignIn(userInfo)
}