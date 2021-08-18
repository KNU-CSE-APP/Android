package com.example.knucseapp.data.request

import com.example.knucseapp.data.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReservationService {

    @GET("classRoom/searchSeats/{building}/{roomNumber}")
    fun classState(@Path("building") building: String, @Path("roomNumber") roomNumber: Int): ApiResult<List<ClassSeatDTO>>

    @POST("/reservation/reservation")
    fun makeReservation(@Body reservationDTO: ReservationDTO): ApiResult<String>

    @POST("reservation/findReservation")
    fun myReservation(): ApiResult<FindReservationDTO>
}