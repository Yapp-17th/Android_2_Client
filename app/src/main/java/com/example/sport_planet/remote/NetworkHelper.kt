package com.example.sport_planet.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkHelper {
    private const val token =
        //"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJoYWVtaW4iLCJpYXQiOjE2MDY5NjMyNjAsImV4cCI6MTYzODQ5OTI2MiwiYXVkIjoiIiwic3ViIjoiaGFlbWluQGV4YW1wbGUuY29tIiwidXNlcklkIjoiMyJ9.3idHYLa5gygsPMJhsiAj9uqiaLt2i6rDx4_POEy86yY"
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJzb3llb24iLCJpYXQiOjE2MDY5NjMxNDgsImV4cCI6MTYzODQ5OTE1MCwiYXVkIjoiIiwic3ViIjoic295ZW9uQGV4YW1wbGUuY29tIiwidXNlcklkIjoiMSJ9.zef85bZet10T8GTIfFgC8pNalFlf3I0Ae48aDXnPatw"
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
        .baseUrl("http://101.101.219.23/api/")
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: Api = retrofit.create(Api::class.java)
}