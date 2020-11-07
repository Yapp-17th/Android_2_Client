package com.example.sport_planet.remote

//import com.example.sport_planet.model.LoginResponse
//import com.example.sport_planet.model.ServerCallBackResponse
import com.example.sport_planet.model.ExerciseResponse
import com.example.sport_planet.model.RegionResponse
import io.reactivex.Single

interface RemoteDataSource {
    fun getExercise(): Single<ExerciseResponse>

    fun getRegion(): Single<RegionResponse>

//    fun postSignIn(userInfo: LoginResponse): Single<ServerCallBackResponse>
}