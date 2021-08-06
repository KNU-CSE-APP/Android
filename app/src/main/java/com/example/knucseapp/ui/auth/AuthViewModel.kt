package com.example.knucseapp.ui.auth

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knucseapp.R
import com.example.knucseapp.data.model.AuthResponse
import com.example.knucseapp.data.model.SignUpForm
import com.example.knucseapp.data.model.VerifyEmailDTO
import com.example.knucseapp.data.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    var email = ObservableField<String>()
    var permissionCode = ObservableField<String>()
    var password = ObservableField<String>()
    var passwordConfirm = ObservableField<String>()
    var username = ObservableField<String>()
    var nickname = ObservableField<String>()
    var studentId = ObservableField<String>()
    var majorRadio = ObservableField<Int>()
    var genderRadio = ObservableField<Int>()

    private val _getResponse : MutableLiveData<AuthResponse> = MutableLiveData()
    val getResponse : LiveData<AuthResponse> get() = _getResponse

    private val _verifyPostResponse : MutableLiveData<AuthResponse> = MutableLiveData()
    val verifyPostResponse : LiveData<AuthResponse> get() = _verifyPostResponse

    private val _signUpResponse : MutableLiveData<AuthResponse> = MutableLiveData()
    val signUpResponse : LiveData<AuthResponse> get() = _signUpResponse

    fun getVerifyCode() = viewModelScope.launch {
        _getResponse.value = authRepository.requestVerifyCode(email.get()!!)
    }

    fun postVerify() = viewModelScope.launch {
        _verifyPostResponse.value = authRepository.requestVerify(
            VerifyEmailDTO(email.get()!!+"@knu.ac.kr",permissionCode.get()!!))
    }

    fun postSignUP() = viewModelScope.launch {
        var gender = when(genderRadio.get()){
            R.id.gender_radiobutton_male -> "MALE"
            else -> "FEMALE"
        }
        var major = when(majorRadio.get()){
            R.id.major_radiobutton_advanced -> "ADVANCED"
            else -> "GLOBAL"
        }
        _signUpResponse.value = authRepository.requestSignUp(
            SignUpForm(email.get()!!+"@knu.ac.kr",gender,major,nickname.get()!!,password.get()!!,permissionCode.get()!!,studentId.get()!!,username.get()!!)
        )
    }

}