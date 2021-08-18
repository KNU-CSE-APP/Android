package com.example.knucseapp.ui.reservation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.knucseapp.data.model.ClassSeatDTO
import com.example.knucseapp.data.model.CommentDTO
import com.example.knucseapp.data.repository.ReservationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReservationViewModel(private val repository: ReservationRepository) : ViewModel() {

    private val _allSeatDataLoading = MutableLiveData<Boolean>()
    val allSeatDataLoading: LiveData<Boolean> get() = _allSeatDataLoading

    private val _allSeatData = MutableLiveData<List<ClassSeatDTO>>()
    val allSeatData: LiveData<List<ClassSeatDTO>> get() = _allSeatData


    fun getAllSeat(building: String, roomNum: Int){
        _allSeatDataLoading.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            repository.classState(building, roomNum)?.let {
                if(it.success) {
                    _allSeatData.postValue(it.response!!)
                }
                _allSeatDataLoading.postValue(false)
            }
        }
    }


}