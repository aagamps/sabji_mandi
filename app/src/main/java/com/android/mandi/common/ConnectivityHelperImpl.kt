package com.android.mandi.common

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.kredily.app.dependency.common.ConnectivityHelper
import javax.inject.Inject

/**
 * Helper class to get status of mobile device connectivity
 * This class implements the contract specified by [com.kredily.app.dependency.common.ConnectivityHelper]
 *
 * @author Ketan Patel
 */
class ConnectivityHelperImpl @Inject constructor(private val connectivityManager : ConnectivityManager) :
    ConnectivityHelper {
    
    /**
     * Check if there is any internet connectivity
     *
     * @return true when the device is connected to any wifi/data network false otherwise
     */
    override fun isConnected() : Boolean {
        var isConnected = false
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager.let {
                it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                    isConnected = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        else -> false
                    }
                }
            }
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo
            @Suppress("DEPRECATION")
            isConnected = networkInfo != null && networkInfo.isConnected
        }
        return isConnected
    }
}