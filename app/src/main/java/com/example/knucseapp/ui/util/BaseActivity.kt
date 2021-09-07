package com.example.knucseapp.ui.util

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Network 상태 모니터 서비스 라이프사이클 등록 및 시작
        val networkStatusMonitor = NetworkStateMonitor(this)
        lifecycle.addObserver(networkStatusMonitor)
        networkStatusMonitor.enable()
        networkStatusMonitor.initNetworkCheck()
    }
}