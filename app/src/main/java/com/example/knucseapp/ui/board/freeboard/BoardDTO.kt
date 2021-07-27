package com.example.knucseapp.ui.board.freeboard

import java.io.Serializable

data class BoardDTO(
    val board : Board? = null
) : Serializable

data class Board(
    val boardItem: BoardItem? = null,
    val comments : MutableList<Comment>? = null
) : Serializable

data class BoardItem(
    val id : Int? = null,
    val category : String? = null,
    val author: String? = null,
    val title: String? = null,
    val content: String? = null,
    val date : String? = null
) : Serializable

data class Comment(
    val id : Int? = null,
    val author: String? = null,
    val comment : String? = null,
    val date : String? = null,
    val replys : MutableList<Reply>? = null
) : Serializable

data class Reply(
    val id : Int? = null,
    val author: String? = null,
    val comment : String? = null,
    val date : String? = null
) : Serializable