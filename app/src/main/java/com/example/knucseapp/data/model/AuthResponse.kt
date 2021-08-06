package com.example.knucseapp.data.model


import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("error")
    val error: ApiError,
    @SerializedName("response")
    val response: String,
    @SerializedName("success")
    val success: Boolean
)