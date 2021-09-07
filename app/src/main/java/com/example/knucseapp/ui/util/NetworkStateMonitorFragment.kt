package com.example.knucseapp.ui.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NetworkStateMonitorFragment(private val lifecycleOwner: FragmentActivity?): ConnectivityManager.NetworkCallback(), LifecycleObserver {
    private val connectivityManager =
        lifecycleOwner?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    /** Network를 감지할 Capabilities 선언 **/
    private val networkRequest: NetworkRequest = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .build()

    /** 네트워크 상태 체크 **/
    fun initNetworkCheck() {
        val activeNetwork = connectivityManager.activeNetwork
        if (activeNetwork != null) {
            NetworkStatus.status = true
//            Toast.makeText(lifecycleOwner, "네트워크 있음", Toast.LENGTH_SHORT).show()
        } else {
            NetworkStatus.status = false
//            Toast.makeText(lifecycleOwner, "네트워크 없음", Toast.LENGTH_SHORT).show()
        }
    }

    /** Network 모니터링 서비스 시작 **/
    fun enable() {
        if (lifecycleOwner != null) {
            check(lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED))
        }
        connectivityManager.registerNetworkCallback(networkRequest, this)
    }

    /** Network 모니터링 서비스 해제 **/
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun disable() {
        connectivityManager.unregisterNetworkCallback(this)
        if (lifecycleOwner != null) {
            lifecycleOwner.lifecycle.removeObserver(this)
        }
    }

    /** Network가 Available 상태이면 Call **/
    override fun onAvailable(network: Network) {
        super.onAvailable(network)
//        Toast.makeText(lifecycleOwner, "네트워크 있음", Toast.LENGTH_SHORT).show()
        NetworkStatus.status = true
    }

    /** Network가 Available 상태에서 Unavailable로 변경되면 Call **/
    override fun onLost(network: Network) {
        super.onLost(network)
//        Toast.makeText(lifecycleOwner, "네트워크 없음", Toast.LENGTH_SHORT).show()
        NetworkStatus.status = false
    }
}