package com.yapp.sport_planet.remote

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkHelper {

    var token : String = ""

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.NONE
        })
        .addInterceptor {
            val request = it.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            Log.d("okhttp", "request: ${it.request()}")
            Log.d("okhttp", "request header: ${it.request().headers}")
            val response = it.proceed(request)
            Log.d("okhttp","response : $response")
            Log.d("okhttp","response header: ${response.headers}")
            response
        }.build()


    private val retrofit = Retrofit.Builder()
        .baseUrl("http://101.101.219.23/api/")
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: Api = retrofit.create(Api::class.java)
}