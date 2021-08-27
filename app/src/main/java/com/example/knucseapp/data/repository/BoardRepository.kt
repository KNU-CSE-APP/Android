package com.example.knucseapp.data.repository

import com.example.knucseapp.data.model.*
import com.example.knucseapp.data.request.user.ApiRequestFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import retrofit2.http.*

class BoardRepository {

    suspend fun write(category: MultipartBody.Part, content: MultipartBody.Part, deleteUrl: List<MultipartBody.Part>?, file: List<MultipartBody.Part>?, title: MultipartBody.Part) = withContext(Dispatchers.IO){
        ApiRequestFactory.boardService.write(category, content, deleteUrl, file, title)
    }

    suspend fun getBoardDetail(boardId: Int) = withContext(Dispatchers.IO) {
        ApiRequestFactory.boardService.getBoardDetail(boardId)
    }

    suspend fun getAllBoard(category: String, page: Int, size: Int) = withContext(Dispatchers.IO) {
        ApiRequestFactory.boardService.getAllBoard(category, page, size)
    }

    suspend fun changeBoardDetail(category: String, content: String, deleteUrl: List<String>?, file: List<MultipartBody.Part>?, title: String, boardId: Int) = withContext(Dispatchers.IO) {
        ApiRequestFactory.boardService.changeBoardDetail(boardId, category, content, deleteUrl, file, title)
    }

    suspend fun deleteBoardDetail(boardId: Int) = withContext(Dispatchers.IO) {
        ApiRequestFactory.boardService.deleteBoardDetail(boardId)
    }

    suspend fun findContent(content: String) = withContext(Dispatchers.IO) {
        ApiRequestFactory.boardService.findContent(content)
    }

    suspend fun findTitle(title: String) = withContext(Dispatchers.IO) {
        ApiRequestFactory.boardService.findTitle(title)
    }

    suspend fun findAuthor(author: String) = withContext(Dispatchers.IO) {
        ApiRequestFactory.boardService.findAuthor(author)
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