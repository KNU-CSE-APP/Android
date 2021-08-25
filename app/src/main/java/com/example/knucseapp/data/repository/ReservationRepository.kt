package com.example.knucseapp.data.repository

import com.example.knucseapp.data.model.ReservationDTO
import com.example.knucseapp.data.request.user.ApiRequestFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReservationRepository {

    suspend fun classState(building: String, roomNumber: Int) = withContext(Dispatchers.IO) {
        ApiRequestFactory.reservationService.classState(building, roomNumber)
    }

    suspend fun makeReservation(reservationDTO: ReservationDTO) = withContext(Dispatchers.IO) {
        ApiRequestFactory.reservationService.makeReservation(reservationDTO)
    }

    suspend fun myReservation() = withContext(Dispatchers.IO) {
        ApiRequestFactory.reservationService.myReservation()
    }

    suspend fun deleteReservation() = withContext(Dispatchers.IO){
        ApiRequestFactory.reservationService.deleteReservation()
    }

    suspend fun searchAllClassRoom() = withContext(Dispatchers.IO) {
        ApiRequestFactory.reservationService.searchAllClassRoom()
    }

    suspend fun extensionReservation() = withContext(Dispatchers.IO){
        ApiRequestFactory.reservationService.extensionReservation()
    }
}