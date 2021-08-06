package com.example.knucseapp.data.model

import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("error")
    val error: ApiError,
    @SerializedName("response")
    val response: LoginSuccessDTO,
    @SerializedName("success")
    val success: Boolean
)
