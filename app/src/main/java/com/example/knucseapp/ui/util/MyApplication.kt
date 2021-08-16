package com.example.knucseapp.ui.util

import android.app.Application
import com.example.knucseapp.ui.auth.MySharedPreferences

class MyApplication : Application() {
    companion object {
        lateinit var prefs: MySharedPreferences
    }

    override fun onCreate() {
        prefs = MySharedPreferences(applicationContext)
        super.onCreate()
    }
}