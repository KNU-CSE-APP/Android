package com.example.knucseapp.ui.board

import java.io.Serializable

data class BoardDTO(
    val boardItem : BoardItem,
    val comment : Comment
)

data class BoardItem(
    val id : Int? = null,
    val category : String? = null,
    val author: String? = null,
    val title: String? = null,
    val content: String? = null,
    val date : String? = null,
    val CommentCnt : Int? = null
) : Serializable

data class Comment(
    val id : Int? = null,
    val author: String? = null,
    val comment : String? = null,
    val date : String? = null
) : Serializable