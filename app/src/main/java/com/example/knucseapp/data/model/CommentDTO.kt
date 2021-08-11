package com.example.knucseapp.data.model

data class CommentDTO(
    val author: String,
    val boardId: Int,
    val commentId: Int,
    val content: String,
    val parentId: Int,
    val replyList: MutableList<CommentDTO>,
    val time: String
)