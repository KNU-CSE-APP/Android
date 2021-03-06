package com.example.knucseapp.data.request

import com.example.knucseapp.data.model.*
import okhttp3.MultipartBody
import retrofit2.http.*

interface BoardService {

    @Multipart
    @POST("/board/write")
    suspend fun write(@Part category: MultipartBody.Part,
                      @Part content: MultipartBody.Part,
                      @Part deleteUrl : List<MultipartBody.Part>?,
                      @Part file: List<MultipartBody.Part>?,
                      @Part title: MultipartBody.Part) : ApiResult<BoardDTO>

    @GET("/board/{boardId}")
    suspend fun getBoardDetail(@Path("boardId") boardId: Int) : ApiResult<BoardDTO>

    @GET("/board/list")
    suspend fun getAllBoard(@Query("category") category: String,
                            @Query("page") page: Int,
                            @Query("size") size: Int) : ApiResult<Page>

    @Multipart
    @PUT("/board/{boardId}")
    suspend fun changeBoardDetail(@Path("boardId") boardId: Int,
                                  @Part category: MultipartBody.Part,
                                  @Part content: MultipartBody.Part,
                                  @Part deleteUrl : List<MultipartBody.Part>?,
                                  @Part file: List<MultipartBody.Part>?,
                                  @Part title: MultipartBody.Part)  : ApiResult<String>

    @DELETE("/board/{boardId}")
    suspend fun deleteBoardDetail(@Path("boardId") boardId : Int) : ApiResult<String>

    // κ²μ
    @GET("/board/findContent")
    suspend fun findContent(@Query("content") content: String) : ApiResult<List<BoardDTO>>

    @GET("/board/findTitle")
    suspend fun findTitle(@Query("title") title: String) : ApiResult<List<BoardDTO>>

    @GET("/board/findAuthor")
    suspend fun findAuthor(@Query("author") author: String) : ApiResult<List<BoardDTO>>


    // λκΈ
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

    @GET("comment/{commentId}")
    suspend fun getComment(@Path("commentId") commentId: Int): ApiResult<CommentDTO>
}