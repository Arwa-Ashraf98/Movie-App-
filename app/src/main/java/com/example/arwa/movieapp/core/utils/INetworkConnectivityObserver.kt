package com.example.arwa.movieapp.core.utils

import kotlinx.coroutines.flow.Flow

interface INetworkConnectivityObserver {

    fun observeNetworkConnection(): Flow<NetworkStatus>
}