package com.example.knucseapp.ui.reservation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knucseapp.data.model.ApiResult
import com.example.knucseapp.data.model.FindReservationDTO
import com.example.knucseapp.data.repository.ReservationRepository
import kotlinx.coroutines.launch

class ReservationViewModel(private val repository: ReservationRepository) : ViewModel() {

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> get() = _dataLoading

    private val _getFindReservationResponse : MutableLiveData<ApiResult<FindReservationDTO>> = MutableLiveData()
    val getFindReservationResponse : LiveData<ApiResult<FindReservationDTO>> get() = _getFindReservationResponse
    fun requestFindReservation(){
        _dataLoading.postValue(true)
        viewModelScope.launch{
            _getFindReservationResponse.value = repository.myReservation()
            _dataLoading.postValue(false)
        }

    }
}