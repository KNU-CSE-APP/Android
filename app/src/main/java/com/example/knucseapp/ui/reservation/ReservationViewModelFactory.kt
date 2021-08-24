package com.example.knucseapp.ui.reservation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.knucseapp.data.repository.ReservationRepository
import com.example.knucseapp.ui.board.BoardViewModel

class ReservationViewModelFactory(private val repository: ReservationRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ReservationViewModel(repository) as T
    }
}