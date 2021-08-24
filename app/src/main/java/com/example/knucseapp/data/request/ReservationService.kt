package com.example.knucseapp.data.request

import com.example.knucseapp.data.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReservationService {

    @GET("/classRoom/searchSeats/{building}/{roomNumber}")
    suspend fun classState(@Path("building") building: String, @Path("roomNumber") roomNumber: Int): ApiResult<List<ClassSeatDTO>>

    @POST("/reservation/reservation")
    suspend fun makeReservation(@Body reservationDTO: ReservationDTO): ApiResult<String>

    @POST("reservation/findReservation")
    suspend fun myReservation(): ApiResult<FindReservationDTO>

    @POST("reservation/delete")
    suspend fun deleteReservation() : ApiResult<String>

    @GET("/classRoom/searchAllClassRoom")
    suspend fun searchAllClassRoom() : ApiResult<List<FindClassRoomDTO>>
}