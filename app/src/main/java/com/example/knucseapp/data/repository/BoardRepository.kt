package com.example.knucseapp.data.repository

import com.example.knucseapp.data.model.*
import com.example.knucseapp.data.request.user.ApiRequestFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.http.*

class BoardRepository {

    suspend fun write(boardForm: BoardForm) = withContext(Dispatchers.IO){
        ApiRequestFactory.boardService.write(boardForm)
    }

    suspend fun getBoardDetail(boardId: Int) = withContext(Dispatchers.IO) {
        ApiRequestFactory.boardService.getBoardDetail(boardId)
    }

    suspend fun getAllBoard(category: String, page: Int, size: Int) = withContext(Dispatchers.IO) {
        ApiRequestFactory.boardService.getAllBoard(category, page, size)
    }

    suspend fun changeBoardDetail(boardForm: BoardForm, boardId: Int) = withContext(Dispatchers.IO) {
        ApiRequestFactory.boardService.changeBoardDetail(boardForm, boardId)
    }

    suspend fun deleteBoardDetail(boardId: Int) = withContext(Dispatchers.IO) {
        ApiRequestFactory.boardService.deleteBoardDetail(boardId)
    }

    suspend fun commentWrite(commentForm: CommentForm) = withContext(Dispatchers.IO) {
        ApiRequestFactory.boardService.commentWrite(commentForm)
    }

    suspend fun commentReplyWrite(replyForm: ReplyForm) = withContext(Dispatchers.IO){
        ApiRequestFactory.boardService.commentReplyWrite(replyForm)
    }

    suspend fun findCommentsByBoardId(boardId: Int) = withContext(Dispatchers.IO) {
        ApiRequestFactory.boardService.findCommentsByBoardId(boardId)
    }

    suspend fun changeComment(commentForm: CommentForm, commentId: Int) = withContext(Dispatchers.IO) {
        ApiRequestFactory.boardService.changeComment(commentForm, commentId)
    }

    suspend fun deleteComment(commentId: Int) = withContext(Dispatchers.IO) {
        ApiRequestFactory.boardService.deleteComment(commentId)
    }

    suspend fun getMyComments() = withContext(Dispatchers.IO) {
        ApiRequestFactory.boardService.getMyComments()
    }

    suspend fun getComment(commentId: Int) = withContext(Dispatchers.IO) {
        ApiRequestFactory.boardService.getComment(commentId)
    }

}