package com.iotdroid.forecast.ui.weather.current

import androidx.lifecycle.ViewModel
import com.iotdroid.forecast.data.repository.ForecastRepository
import com.iotdroid.forecast.internal.UnitSystem
import com.iotdroid.forecast.internal.lazyDeffered

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {



    private val unitSystem = UnitSystem.METRIC

    val isMetric : Boolean
    get() = unitSystem == UnitSystem.METRIC


    val weather  by lazyDeffered{
        forecastRepository.getCurrentWeatherData(isMetric)
    }
}
