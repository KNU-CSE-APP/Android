package com.example.knucseapp.data.repository

import com.example.knucseapp.data.model.SignUpForm
import com.example.knucseapp.data.model.VerifyEmailDTO
import com.example.knucseapp.data.request.user.ApiRequestFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository{

    suspend fun requestVerifyCode(email : String) = withContext(Dispatchers.IO){
        ApiRequestFactory.userService.requestVerifyCode(email+"@knu.ac.kr")
    }

    suspend fun requestVerify(verifyEmailDTO: VerifyEmailDTO) = withContext(Dispatchers.IO){
        ApiRequestFactory.userService.requestVerify(verifyEmailDTO)
    }

    suspend fun requestSignUp(signUpForm: SignUpForm) = withContext(Dispatchers.IO){
        ApiRequestFactory.userService.signUp(signUpForm)
    }
}
