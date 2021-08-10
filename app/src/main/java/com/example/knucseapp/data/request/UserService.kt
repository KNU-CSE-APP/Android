package com.example.knucseapp.data.request.user

import com.example.knucseapp.data.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface UserService {
    @GET("/user/verify/{requestEmail}")
    suspend fun requestVerifyCode(@Path("requestEmail") email: String) : ApiResult<String>

    @POST("/user/verify")
    suspend fun requestVerify(@Body verifyEmailDTO : VerifyEmailDTO) : ApiResult<String>

    @POST("/user/signUp")
    suspend fun signUp(@Body signUpForm : SignUpForm) : ApiResult<String>

    @POST("/user/signIn")
    suspend fun signIn(@Body signInForm : SignInForm) : ApiResult<LoginSuccessDTO>

    @GET("/user/getUserEmailNickname")
    suspend fun requestUserInfo() : ApiResult<MemberDTO>

    @Multipart
    @PUT("/user/image/nickname")
    suspend fun requestUserInfoEdit(
        @Part image: MultipartBody.Part?,
        @Part nickname: MultipartBody.Part?
    ) : ApiResult<String>

    @PUT("/user/changePassword")
    suspend fun requestUserPasswordEdit(@Body changePasswordForm: ChangePasswordForm) : ApiResult<String>
}