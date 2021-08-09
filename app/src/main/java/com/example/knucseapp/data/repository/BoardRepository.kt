package com.example.knucseapp.data.repository

import com.example.knucseapp.data.model.ApiResult
import com.example.knucseapp.data.model.BoardDTO
import com.example.knucseapp.data.model.BoardForm
import com.example.knucseapp.data.model.Page
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

}