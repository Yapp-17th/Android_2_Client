package com.example.sport_planet.remote

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkHelper {
    private const val token =
    //"eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2MDc1MDQyNTEsImlhdCI6MTYwNzE1ODY1MSwianRpIjoiZTU2MjNhNzAtNTA0Ni00Y2VkLWI2NzUtOGFjZTU4MTYwMjU3IiwidXNlcklkIjoiMSJ9.oo2qv2e0Kjf-YX15XFrCuu486WuP3oKxhodERxaCoXepfJ6KzO_j2c1jPF0cjTLa56W3etiss8Hht2datFjSyA"
    "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2MDc1MDMzNTgsImlhdCI6MTYwNzE1Nzc1OCwianRpIjoiMjJhMzViYjItOWI1Yi00YzRmLThjZjYtMzU1MjBmNTBmY2EzIiwidXNlcklkIjoiMyJ9.9Ha_yquWdpOGoxshm-qUxv9u--34kR3VajQ9GoS5c3joepp2fUbU0wRMbbIu3FYtBJ5SSLuAI6TzvcLxx98bdw"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.NONE
        })
        .addInterceptor {
            Log.d("okhttp", "request: ${it.request()}")
            val request = it.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            val response = it.proceed(request)
            Log.d("okhttp","response : $response")
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