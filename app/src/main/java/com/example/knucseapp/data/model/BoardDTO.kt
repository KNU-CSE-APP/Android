package com.example.knucseapp.data.model

import java.io.Serializable

data class BoardDTO(
    val author: String,
    val boardId: Int,
    val category: String,
    val content: String,
    val time: String,
    val commentCnt: Int,
    val title: String,
    val profileImg: String
) : Serializable
