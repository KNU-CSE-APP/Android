package com.example.knucseapp.ui.data


import com.google.gson.annotations.SerializedName

data class Error(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)