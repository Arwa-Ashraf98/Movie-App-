package com.example.arwa.movieapp.core.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

object NetworkConnectivityObserver  : INetworkConnectivityObserver {

    private lateinit var connectivityManager: ConnectivityManager

    fun initNetworkConnectivityObserver(context: Context)  {
        connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    override fun observeNetworkConnection(): Flow<NetworkStatus> {
       return callbackFlow {
           val callback = object : ConnectivityManager.NetworkCallback() {

               override fun onAvailable(network: Network) {
                   super.onAvailable(network)
                   launch { send(NetworkStatus.AVAILABLE) }

               }

               override fun onUnavailable() {
                   super.onUnavailable()
                   launch { send(NetworkStatus.UNAVAILABLE) }

               }

               override fun onLost(network: Network) {
                   super.onLost(network)
                   launch { send(NetworkStatus.LOST) }
               }

               override fun onLosing(network: Network, maxMsToLive: Int) {
                   super.onLosing(network, maxMsToLive)
                   launch { send(NetworkStatus.LOSING) }
               }
           }

           connectivityManager.registerDefaultNetworkCallback(callback)
           awaitClose {
               connectivityManager.unregisterNetworkCallback(callback)
           }
       }.distinctUntilChanged()
    }
}