package com.example.knucseapp.ui.auth

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knucseapp.R
import com.example.knucseapp.data.model.*
import com.example.knucseapp.data.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    var signUpEmail = ObservableField<String>()
    var permissionCode = ObservableField<String>()
    var signUpPassword = ObservableField<String>()
    var passwordConfirm = ObservableField<String>()
    var username = ObservableField<String>()
    var nickname = ObservableField<String>()
    var studentId = ObservableField<String>()
    var majorRadio = ObservableField<Int>()
    var genderRadio = ObservableField<Int>()

    var signInEmail = ObservableField<String>()
    var signInPassword = ObservableField<String>()

    private val _getResponse : MutableLiveData<AuthResponse> = MutableLiveData()
    val getResponse : LiveData<AuthResponse> get() = _getResponse

    private val _verifyPostResponse : MutableLiveData<AuthResponse> = MutableLiveData()
    val verifyPostResponse : LiveData<AuthResponse> get() = _verifyPostResponse

    private val _signUpResponse : MutableLiveData<AuthResponse> = MutableLiveData()
    val signUpResponse : LiveData<AuthResponse> get() = _signUpResponse

    private val _signInResponse : MutableLiveData<SignInResponse> = MutableLiveData()
    val signInResponse : LiveData<SignInResponse> = _signInResponse

    fun getVerifyCode() = viewModelScope.launch {
        _getResponse.value = authRepository.requestVerifyCode(signUpEmail.get()!!)
    }

    fun postVerify() = viewModelScope.launch {
        _verifyPostResponse.value = authRepository.requestVerify(
            VerifyEmailDTO(signUpEmail.get()!!+"@knu.ac.kr",permissionCode.get()!!))
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
            SignUpForm(signUpEmail.get()!!+"@knu.ac.kr",gender,major,nickname.get()!!,signUpPassword.get()!!,permissionCode.get()!!,studentId.get()!!,username.get()!!)
        )
    }

    fun postSignIn() = viewModelScope.launch {
        _signInResponse.value = authRepository.requestSignIn(
            SignInForm(signInEmail.get()!!+"@knu.ac.kr",signInPassword.get()!!)
        )
    }

}