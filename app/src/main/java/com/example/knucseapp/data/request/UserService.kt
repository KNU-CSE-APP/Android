package com.example.knucseapp.data.request.user

import com.example.knucseapp.data.model.*
import okhttp3.MultipartBody
import retrofit2.http.*

interface UserService {
    // 회원 가입 인증 번호 전송
    @GET("/user/verify/{requestEmail}")
    suspend fun requestVerifyCode(@Path("requestEmail") email: String) : ApiResult<String>

    // 비밀번호 찾기 인증 번호 전송
    @GET("/user/findPassword/{requestEmail}")
    suspend fun requestFindPasswordCode(@Path("requestEmail") email: String) : ApiResult<String>

    // 이메일 인증번호 검증
    @POST("/user/verify")
    suspend fun requestVerify(@Body verifyEmailDTO : VerifyEmailDTO) : ApiResult<String>

    // 비밀번호 찾기 인증번호 검증
    @POST("/user/verifyPassword")
    suspend fun requestVerifyPassword(@Body verifyEmailDTO: VerifyEmailDTO) : ApiResult<String>

    // 비번 인증번호 검증 후 변경
    @POST("/user/changeValidatedPassword")
    suspend fun requestChangeValidatedPassword(@Body validatedPasswordForm: ValidatedPasswordForm) : ApiResult<String>

    @POST("/user/signUp")
    suspend fun signUp(@Body signUpForm : SignUpForm) : ApiResult<String>

    @POST("/user/signIn")
    suspend fun signIn(@Body signInForm : SignInForm) : ApiResult<LoginSuccessDTO>

    @GET("/user/getUserEmailNickname")
    suspend fun requestUserInfo() : ApiResult<MemberDTO>

    @Multipart
    @PUT("/user/image/nickname")
    suspend fun requestUserInfoEdit(@Part image: MultipartBody.Part?, @Part nickname: MultipartBody.Part?) : ApiResult<UpdateNickNameAndImageDTO>

    @PUT("/user/changePassword")
    suspend fun requestUserPasswordEdit(@Body changePasswordForm: ChangePasswordForm) : ApiResult<String>

    @GET("/board/findMyBoards")
    suspend fun getMyBoards() : ApiResult<List<BoardDTO>>
  
    @HTTP(method = "DELETE", path = "/user/deleteMember", hasBody = true)
    suspend fun requestDeleteMember(@Body deleteForm: DeleteForm) : ApiResult<String>

    @DELETE("/user/profileimage")
    suspend fun requestDeleteProfileImage() : ApiResult<String>

    @POST("/user/logout")
    suspend fun requestUserLogout() : ApiResult<String>
}