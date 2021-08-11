package com.example.knucseapp.data.model

data class Page(val content: List<BoardDTO>,
                val empty: Boolean,
                val first: Boolean,
                val last: Boolean,
                val number: Int,
                val numberOfElements: Int,
                val pageable: Pageable,
                val size: Int,
                val sort: Sort,
                val totalElements: Int,
                val totalPages: Int
)
