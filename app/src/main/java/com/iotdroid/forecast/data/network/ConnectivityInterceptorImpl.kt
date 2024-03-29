package com.iotdroid.forecast.data.network

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.getSystemService
import com.iotdroid.forecast.internal.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptorImpl (
    context: Context
): ConnectivityInterceptor {
    val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isOnline())
            throw NoConnectivityException()
        return chain.proceed(chain.request())
    }

    fun isOnline():Boolean{
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkInfo = connectivityManager.activeNetworkInfo

        return  networkInfo!= null && networkInfo.isConnected


    }
}