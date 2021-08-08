package com.example.knucseapp.data.request.user

import com.example.knucseapp.data.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    @GET("/user/verify/{requestEmail}")
    suspend fun requestVerifyCode(@Path("requestEmail") email: String) : ApiResult<String>

    @POST("/user/verify")
    suspend fun requestVerify(@Body verifyEmailDTO : VerifyEmailDTO) : ApiResult<String>

    @POST("/user/signUp")
    suspend fun signUp(@Body signUpForm : SignUpForm) : ApiResult<String>

    @POST("/user/signIn")
    suspend fun signIn(@Body signInForm : SignInForm) : ApiResult<LoginSuccessDTO>
}