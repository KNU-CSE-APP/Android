package com.example.knucseapp.ui.request.user

import com.example.knucseapp.ui.data.AuthResponse
import com.example.knucseapp.ui.data.SignUpForm
import com.example.knucseapp.ui.data.VerifyEmailDTO
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    @GET("/user/verify/{requestEmail}")
    suspend fun getVerifyCode(@Path("requestEmail") email: String) : AuthResponse

    @POST("/user/verify")
    suspend fun verify(@Body verifyEmailDTO : VerifyEmailDTO) : AuthResponse

    @POST("/user/signUp")
    suspend fun signUp(@Body signUpForm : SignUpForm) : AuthResponse
}