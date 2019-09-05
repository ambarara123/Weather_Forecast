package com.iotdroid.forecast.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iotdroid.forecast.data.WeatherApiService
import com.iotdroid.forecast.data.network.response.CurrentWeatherResponse
import com.iotdroid.forecast.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl(
    private val apiService: WeatherApiService
) : WeatherNetworkDataSource {
    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()

    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun updateData(location: String, language: String) {
        try {
            val fetchCurrentWeather = apiService
                .getCurrentWeatherData(location,language)
                .await()
                _downloadedCurrentWeather.postValue(fetchCurrentWeather)
        }catch (e : NoConnectivityException){
            Log.e("Connectivity","No Internet Connection. "+e)
        }
    }
}