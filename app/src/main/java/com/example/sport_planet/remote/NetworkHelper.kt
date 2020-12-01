package com.example.sport_planet.remote

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkHelper {
    private const val token =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ0ZXN0ZXIxIiwiaWF0IjoxNjA0NjY3MzA5LCJleHAiOjE2MzYyMDMzMTEsImF1ZCI6IiIsInN1YiI6InRlc3RlcjFAZ21haWwuY29tIiwidXNlcklkIjoiMSJ9.Bmbhc-I1r-L-dW5vUzvB9jRsPPKtcqYXutAyWKqkPrc"
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.NONE
        })
        .addInterceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .addHeader("userId", "1")
                .build()
            Log.d("okhttp", "request : $request")
            val response = chain.proceed(request)
            Log.d("okhttp", "response : $response")
            response
        }.build()

    /*
         현재 알렉스님과 소연님의 서버가 다르기때문에 baseUrl를 따로 설정해야돼서 임시 방편으로
         변수를 여러개 해서 사용합니다.
         추후에 합쳐지면 변수를 하나로 수정하는 작업을 진행 하겠습니다 - 민호 -
     */
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://52.78.52.254/") // 채팅
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: Api = retrofit.create(Api::class.java)
}