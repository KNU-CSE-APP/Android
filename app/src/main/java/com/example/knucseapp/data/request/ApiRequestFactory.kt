package com.example.knucseapp.data.request.user

import com.example.knucseapp.data.request.BoardService
import com.example.knucseapp.data.request.ReservationService
import okhttp3.CookieJar
import okhttp3.Interceptor
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager

object ApiRequestFactory {
    private const val baseUrl = "http://3.34.14.12"

    val headerInterceptor = Interceptor {
        val request = it.request()
            .newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Content-Type","multipart/form-data")
            .build()
        return@Interceptor it.proceed(request)
    }

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BODY })
            .addInterceptor(headerInterceptor)
            .cookieJar(JavaNetCookieJar(CookieManager()))
            .build()
        ).build()

    val userService : UserService by lazy{
        retrofit.create(UserService::class.java)
    }

    val boardService : BoardService by lazy {
        retrofit.create(BoardService::class.java)
    }

    val reservationService : ReservationService by lazy {
        retrofit.create(ReservationService::class.java)
    }
}