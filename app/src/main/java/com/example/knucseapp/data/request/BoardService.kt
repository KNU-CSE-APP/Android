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
                            @Query("size") size: Int) : ApiResult<Page>

    @PUT("/board/{boardId}")
    suspend fun changeBoardDetail(@Body boardForm: BoardForm) : ApiResult<String>

    @DELETE("/board/{boardId}")
    suspend fun deleteBoardDetail(@Path("boardId") boardId : Int) : ApiResult<String>


    // 댓글
    @POST("/comment/write")
    suspend fun commentWrite(@Body commentForm: CommentForm) : ApiResult<CommentDTO>

    @POST("/comment/reply/write")
    suspend fun commentReplyWrite(@Body replyForm: ReplyForm) : ApiResult<CommentDTO>

    @GET("comment/findContentsByBoardId")
    suspend fun findCommentsByBoardId(@Query("boardId") boardId: Int) : ApiResult<List<CommentDTO>>

    @PUT("comment/{commentId}")
    suspend fun changeComment(
        @Body commentForm: CommentForm,
        @Path ("commentId") commentId: Int) : ApiResult<String>

    @DELETE("comment/{commentId}")
    suspend fun deleteComment(@Path("commentId") commentId: Int) : ApiResult<String>

    @GET("comment/getAllComments")
    suspend fun getMyComments() : ApiResult<List<CommentDTO>>
}