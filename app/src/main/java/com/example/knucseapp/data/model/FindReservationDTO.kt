package com.example.knucseapp.data.model

data class FindReservationDTO(
        val building: String,
        val roomNumber: Int,
        val seatNumber: Int,
        val dueDate : String,
        val startDate : String,
        val extensionNum : String
)
