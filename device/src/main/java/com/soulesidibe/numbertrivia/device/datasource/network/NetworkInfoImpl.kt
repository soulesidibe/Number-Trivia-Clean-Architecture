package com.soulesidibe.numbertrivia.device.datasource.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.soulesidibe.numbertrivia.data.network.NetworkInfo

internal class NetworkInfoImpl(private val context: Context): NetworkInfo {
    override suspend fun isConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val capability = cm.getNetworkCapabilities(cm.activeNetwork)
            capability?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
        } else {

            val networkInfo = cm.activeNetworkInfo
            networkInfo != null && networkInfo.isConnected
        }
    }
}