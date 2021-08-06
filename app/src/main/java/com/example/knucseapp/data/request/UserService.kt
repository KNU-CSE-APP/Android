package com.example.knucseapp.data.request.user

import com.example.knucseapp.data.model.*
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

    @POST("/user/signIn")
    suspend fun signIn(@Body signInForm : SignInForm) : SignInResponse
}