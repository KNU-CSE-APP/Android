package com.example.knucseapp.ui.board

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.knucseapp.data.repository.AuthRepository
import com.example.knucseapp.data.repository.BoardRepository
import com.example.knucseapp.ui.auth.AuthViewModel

class BoardViewModelFactory(private val repository: BoardRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BoardViewModel(repository) as T
    }
}