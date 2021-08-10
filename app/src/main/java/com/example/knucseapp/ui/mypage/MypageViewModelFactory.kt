package com.example.knucseapp.ui.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.knucseapp.data.repository.AuthRepository
import com.example.knucseapp.ui.auth.AuthViewModel

class MypageViewModelFactory(private val repository : AuthRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MypageViewModel(repository) as T
    }
}