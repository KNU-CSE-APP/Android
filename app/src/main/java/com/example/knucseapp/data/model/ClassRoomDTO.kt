package com.example.knucseapp.data.model

import java.io.Serializable

data class ClassRoomDTO(
        val building: String,
        val roomNumber: Int,
        val totalSeatNumber: Int
) : Serializable
