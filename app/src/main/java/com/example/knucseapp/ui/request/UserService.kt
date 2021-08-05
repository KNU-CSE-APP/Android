package com.example.knucseapp.ui.request.user

import com.example.knucseapp.ui.data.AuthResponse
import com.example.knucseapp.ui.data.SignUpForm
import com.example.knucseapp.ui.data.VerifyEmailDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    @GET("/user/verify/{requestEmail}")
    suspend fun requestVerifyCode(@Path("requestEmail") email: String) : AuthResponse

    @POST("/user/verify")
    suspend fun requestVerify(@Body verifyEmailDTO : VerifyEmailDTO) : AuthResponse

    @POST("/user/signUp")
    suspend fun signUp(@Body signUpForm : SignUpForm) : AuthResponse
}