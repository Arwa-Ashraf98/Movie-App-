package com.example.arwa.movieapp.app

import android.app.Application
import com.example.arwa.movieapp.core.utils.NetworkConnectivityObserver
import com.example.arwa.pagingexample.utils.NetworkChecker
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        NetworkConnectivityObserver.initNetworkConnectivityObserver(this)
        NetworkChecker().initNetworkChecker(this)
    }
}