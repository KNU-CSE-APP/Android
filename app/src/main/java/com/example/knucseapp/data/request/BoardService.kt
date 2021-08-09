package com.example.knucseapp.data.request

import com.example.knucseapp.data.model.*
import retrofit2.http.*

interface BoardService {

    @POST("/board/write")
    suspend fun write(@Body boardForm : BoardForm) : ApiResult<BoardDTO>

    @GET("/board/{boardId}")
    suspend fun getBoardDetail(@Path("boardId") boardId: Int) : ApiResult<BoardDTO>

    @GET("/board/list")
    suspend fun getAllBoard(@Query("category") category: String,
                            @Query("page") page: Int,
                            @Query("size") size: Int)
    :ApiResult<Page>
}