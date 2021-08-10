package com.example.knucseapp.ui.auth

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knucseapp.R
import com.example.knucseapp.data.model.*
import com.example.knucseapp.data.repository.AuthRepository
import com.example.knucseapp.ui.util.MyApplication
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    // signUp field
    var signUpEmail = ObservableField<String>()
    var permissionCode = ObservableField<String>()
    var signUpPassword = ObservableField<String>()
    var passwordConfirm = ObservableField<String>()
    var username = ObservableField<String>()
    var nickname = ObservableField<String>()
    var studentId = ObservableField<String>()
    var majorRadio = ObservableField<Int>()
    var genderRadio = ObservableField<Int>()

    // signIn field
    var signInEmail = ObservableField<String>()
    var signInPassword = ObservableField<String>()
    var isSelected = ObservableField<Boolean>()

    //auth listener
    var authSignUpListener: AuthListener? = null
    var authSignInListener: AuthListener? = null

    private val _getResponse : MutableLiveData<ApiResult<String>> = MutableLiveData()
    val getResponse : LiveData<ApiResult<String>> get() = _getResponse

    private val _verifyPostResponse : MutableLiveData<ApiResult<String>> = MutableLiveData()
    val verifyPostResponse : LiveData<ApiResult<String>> get() = _verifyPostResponse

    private val _signUpResponse : MutableLiveData<ApiResult<String>> = MutableLiveData()
    val signUpResponse : LiveData<ApiResult<String>> get() = _signUpResponse

    private val _signInResponse : MutableLiveData<ApiResult<LoginSuccessDTO>> = MutableLiveData()
    val signInResponse : LiveData<ApiResult<LoginSuccessDTO>> = _signInResponse

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
            else ->"GLOBAL"
        }
        _signUpResponse.value = authRepository.requestSignUp(
            SignUpForm(signUpEmail.get()!!+"@knu.ac.kr",gender,major,nickname.get()!!,signUpPassword.get()!!,permissionCode.get()!!,studentId.get()!!,username.get()!!)
        )
    }

    fun postSignIn() = viewModelScope.launch {
        if(isSelected.get()!!){
            MyApplication.prefs.setUserId(signInEmail.get()!!)
            MyApplication.prefs.setUserPass(signInPassword.get()!!)
        }
        _signInResponse.value = authRepository.requestSignIn(
            SignInForm(signInEmail.get()!!+"@knu.ac.kr",signInPassword.get()!!)
        )
    }

    fun checkSignInFeild(){
        if(signInEmail.get().isNullOrEmpty()){
            authSignInListener?.onFailure("이메일을 입력해주세요",0)
            return
        }
        if(signInPassword.get().isNullOrEmpty()){
            authSignInListener?.onFailure("비밀번호를 입력해주세요",1)
            return
        }

        postSignIn()

    }
    fun checkSignUpFeild(){
        if(signUpPassword.get().isNullOrEmpty()){
            authSignUpListener?.onFailure("비밀번호를 입력해주세요",1)
            return
        }
        if(passwordConfirm.get().isNullOrEmpty()){
            authSignUpListener?.onFailure("비밀번호 재확인을 해주세요",2)
            return
        }
        if(username.get().isNullOrEmpty()){
            authSignUpListener?.onFailure("이름을 입력해주세요",3)
            return
        }
        if (nickname.get().isNullOrEmpty()){
            authSignUpListener?.onFailure("닉네임을 입력해주세요",4)
            return
        }
        if (studentId.get().isNullOrEmpty()){
            authSignUpListener?.onFailure("학번을 입력해주세요",5)
            return
        }
        if (majorRadio.get()!=R.id.major_radiobutton_advanced && majorRadio.get()!=R.id.major_radiobutton_global){
            authSignUpListener?.onFailure("학과를 선택해주세요",6)
            return
        }
        if (genderRadio.get()!=R.id.gender_radiobutton_female && genderRadio.get()!=R.id.gender_radiobutton_male){
            authSignUpListener?.onFailure("성별을 선택해주세요",7)
            return
        }
        postSignUP()
    }

    fun removeEditText(){
        signInEmail.set("")
        signInPassword.set("")
    }

    fun getSharedPreference(){
        Log.d("prefs",MyApplication.prefs.getUserId())
        Log.d("prefs",MyApplication.prefs.getUserPass())
        if(!MyApplication.prefs.getUserId()!!.equals("") && !MyApplication.prefs.getUserPass()!!.equals("")) {
            Log.d("prefs","in if ")
            signInEmail.set(MyApplication.prefs.getUserId()!!)
            signInPassword.set(MyApplication.prefs.getUserPass()!!)
            checkSignInFeild()
        }
        else
            return
    }
    
}