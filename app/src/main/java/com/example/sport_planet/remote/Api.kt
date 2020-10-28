package com.example.sport_planet.remote

import com.example.sport_planet.model.ExerciseResponse
import com.example.sport_planet.model.LoginResponse
import com.example.sport_planet.model.RegionResponse
import com.example.sport_planet.model.ServerCallBackResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @GET("/v1/exercise")
    fun getExercise(): Single<ExerciseResponse>

    @GET("/v1/address/city")
    fun getRegion(): Single<RegionResponse>

    @POST("/user/sign-in")
    fun postSignIn(@Body userInfo: LoginResponse): Single<ServerCallBackResponse>
}