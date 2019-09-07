package com.iotdroid.forecast.data.repository

import androidx.lifecycle.LiveData
import com.iotdroid.forecast.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry

interface ForecastRepository {
    suspend fun getCurrentWeatherData(metric: Boolean) : LiveData<out UnitSpecificCurrentWeatherEntry>
}