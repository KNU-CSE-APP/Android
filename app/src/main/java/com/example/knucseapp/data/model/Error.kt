package com.example.knucseapp.data.model


import com.google.gson.annotations.SerializedName

data class Error(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)