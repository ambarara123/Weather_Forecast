package com.iotdroid.forecast.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.iotdroid.forecast.data.db.entity.CURRENT_WEATHER_ID
import com.iotdroid.forecast.data.db.entity.Current
import com.iotdroid.forecast.data.db.unitlocalized.ImperialCurrentWeatherEntry
import com.iotdroid.forecast.data.db.unitlocalized.MetricCurrentWeatherEntry

@Dao
interface CurrentWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun Upsert(current: Current)

    @Query("SELECT * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherMatric() : LiveData<MetricCurrentWeatherEntry>

    @Query("SELECT * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherImperial() : LiveData<ImperialCurrentWeatherEntry>

    @Update
    fun updateWeatherCurrent(current: Current)

    @Delete
    fun deleteWeather(current: Current)
}
