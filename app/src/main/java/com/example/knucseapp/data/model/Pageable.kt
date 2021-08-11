package com.example.knucseapp.data.model

data class Pageable(val offset: Int,
                    val pageNumber: Int,
                    val pageSize: Int,
                    val paged: Boolean,
                    val sort: Sort,
                    val unpaged: Boolean)
