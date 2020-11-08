package com.example.sport_planet.remote

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkHelper {
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.NONE
        })
        .addInterceptor {
            val request = it.request()
                .newBuilder()
                .addHeader(
                    "Authorization",
                    "Bearer eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2MDQ3NTQ2MjAsImlhdCI6MTYwNDY2ODIyMCwianRpIjoiNjAxZjhlZjUtMDlkZC00M2E4LWIxNDEtMmVkYzIzMzhmOWFiIiwidXNlcklkIjoiMSJ9.iGcz5rOzHLIOKaRFwic5gPSQkVGo894UE1cqJDOfd1KFs0h30Om7osRQ94wJaqIK0KZ8bP8zsTK2dE4QGcoHWw"
                )
                .build()
            it.proceed(request)
        }.build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://35.173.244.190:8080")
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: Api = retrofit.create(Api::class.java)

}