package com.example.knucseapp.ui.util

import android.os.Bundle
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val networkStatusMonitor = NetworkStateMonitorFragment(activity)
        lifecycle.addObserver(networkStatusMonitor)
        networkStatusMonitor.enable()
        networkStatusMonitor.initNetworkCheck()
    }
}