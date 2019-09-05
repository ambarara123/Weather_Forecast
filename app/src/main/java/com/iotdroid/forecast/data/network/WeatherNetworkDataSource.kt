package com.iotdroid.forecast.data.network

import androidx.lifecycle.LiveData
import com.iotdroid.forecast.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather : LiveData<CurrentWeatherResponse>

    suspend fun updateData(
        location : String,
        language : String
    )
}
