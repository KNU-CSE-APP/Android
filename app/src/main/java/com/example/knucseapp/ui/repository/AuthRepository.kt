package com.example.knucseapp.ui.repository

import com.example.knucseapp.ui.data.VerifyEmailDTO
import com.example.knucseapp.ui.request.user.ApiRequestFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository{

    suspend fun requestVerifyCode(email : String) = withContext(Dispatchers.IO){
        ApiRequestFactory.userService.getVerifyCode(email+"@knu.ac.kr")
    }

    suspend fun requestVerify(verifyEmailDTO: VerifyEmailDTO) = withContext(Dispatchers.IO){
        ApiRequestFactory.userService.verify(verifyEmailDTO)
    }
}
