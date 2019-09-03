package com.iotdroid.forecast.data.network.response

import com.iotdroid.forecast.data.db.entity.Current
import com.iotdroid.forecast.data.db.entity.Location


data class CurrentWeatherResponse(
    val location: Location,
    val current: Current
)
