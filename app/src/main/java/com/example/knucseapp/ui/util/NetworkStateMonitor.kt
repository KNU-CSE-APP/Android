package com.example.knucseapp.ui.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.knucseapp.ui.auth.AuthViewModel

class NetworkStateMonitor(private val lifecycleOwner: AppCompatActivity):ConnectivityManager.NetworkCallback(),LifecycleObserver {
    private val connectivityManager =
        lifecycleOwner.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private lateinit var authViewModel: AuthViewModel

    /** Network를 감지할 Capabilities 선언 **/
    private val networkRequest: NetworkRequest = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .build()

    /** 네트워크 상태 체크 **/
    fun initNetworkCheck() {
        val activeNetwork = connectivityManager.activeNetwork
        if (activeNetwork != null) {
//            Toast.makeText(lifecycleOwner, "네트워크 있음", Toast.LENGTH_SHORT).show()
            NetworkStatus.status = true
        } else {
//            Toast.makeText(lifecycleOwner, "네트워크 없음", Toast.LENGTH_SHORT).show()
            NetworkStatus.status = false
        }
    }

    /** Network 모니터링 서비스 시작 **/
    fun enable() {
        check(lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED))
        connectivityManager.registerNetworkCallback(networkRequest, this)
    }

    /** Network 모니터링 서비스 해제 **/
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun disable() {
        connectivityManager.unregisterNetworkCallback(this)
        lifecycleOwner.lifecycle.removeObserver(this)
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
