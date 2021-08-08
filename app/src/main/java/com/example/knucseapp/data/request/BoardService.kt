package com.example.knucseapp.data.request

import com.example.knucseapp.data.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BoardService {

    @POST("/board/write")
    suspend fun write(@Body boardForm : BoardForm) : ApiResult<BoardDTO>

    @GET("/board/{boardId}")
    suspend fun requestVerifyCode(@Path("boardId") boardId: Int) : ApiResult<BoardDTO>
}