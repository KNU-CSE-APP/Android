package com.example.knucseapp.data.model

import java.io.Serializable

data class FindClassRoomDTO(
        val building: String,
        val reservedSeatNumber : Int,
        val roomNumber: Int,
        val totalSeatNumber:Int
) : Serializable
