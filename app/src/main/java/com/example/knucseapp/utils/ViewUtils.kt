package com.example.knucseapp.utils

import android.content.Context
import android.content.Intent
import com.example.knucseapp.ui.SignInActivity

fun Context.startSignInActivity() =
        Intent(this, SignInActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }