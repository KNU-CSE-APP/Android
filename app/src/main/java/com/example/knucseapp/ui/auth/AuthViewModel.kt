package com.example.knucseapp.ui.auth

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knucseapp.ui.data.AuthResponse
import com.example.knucseapp.ui.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {


    var email = ObservableField<String>()
    var permissionCode = ObservableField<String>()
    var password = ObservableField<String>()

    private val _getResponse : MutableLiveData<AuthResponse> = MutableLiveData()
    val getResponse : LiveData<AuthResponse> get() = _getResponse

    fun requestVerifyCode() = viewModelScope.launch {
        _getResponse.value = authRepository.requestVerifyCode(email.get()!!)
    }

}