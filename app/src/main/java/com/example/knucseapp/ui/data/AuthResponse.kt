package com.example.knucseapp.ui.data


import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("error")
    val error: Error,
    @SerializedName("response")
    val response: String,
    @SerializedName("success")
    val success: Boolean
)