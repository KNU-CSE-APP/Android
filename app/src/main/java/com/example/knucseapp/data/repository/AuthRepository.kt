package com.example.knucseapp.data.repository

import com.example.knucseapp.data.model.*
import com.example.knucseapp.data.request.user.ApiRequestFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody

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

    suspend fun requestSignIn(signInForm: SignInForm) = withContext(Dispatchers.IO){
        ApiRequestFactory.userService.signIn(signInForm)
    }

    suspend fun requestUserInfo() = withContext(Dispatchers.IO){
        ApiRequestFactory.userService.requestUserInfo()
    }

    suspend fun requestEditUserInfo(image : MultipartBody.Part?, nickName : MultipartBody.Part?) = withContext(Dispatchers.IO){
        ApiRequestFactory.userService.requestUserInfoEdit(image,nickName)
    }

    suspend fun requestChangePassword(changePasswordForm: ChangePasswordForm) = withContext(Dispatchers.IO){
        ApiRequestFactory.userService.requestUserPasswordEdit(changePasswordForm)
    }

    suspend fun requestDeleteMember(deleteForm: DeleteForm) = withContext(Dispatchers.IO){
        ApiRequestFactory.userService.requestDeleteMember(deleteForm)
    }
}
