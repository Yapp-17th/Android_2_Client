package com.example.sport_planet.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkHelper {
    private const val token =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ0ZXN0ZXIzIiwiaWF0IjoxNjA2NDY0NjkyLCJleHAiOjE2MzgwMDA2OTUsImF1ZCI6IiIsInN1YiI6InRlc3RlcjNAZXhhbXBsZS5jb20iLCJ1c2VySWQiOiIzIn0.xTD558ZkoXQ4eBRSO4sL8BKQkD-Os-BdtvKWuWcu_J0"
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.NONE
        })
        .addInterceptor {
            val request = it.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            it.proceed(request)
        }.build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://52.78.52.254/api/")
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: Api = retrofit.create(Api::class.java)

}