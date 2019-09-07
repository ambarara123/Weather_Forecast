package com.iotdroid.forecast.data.repository

import androidx.lifecycle.LiveData
import com.iotdroid.forecast.data.db.CurrentWeatherDao
import com.iotdroid.forecast.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry
import com.iotdroid.forecast.data.network.WeatherNetworkDataSource
import com.iotdroid.forecast.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import java.util.*

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val currentWeatherdataSource: WeatherNetworkDataSource
) : ForecastRepository {
    init {
        currentWeatherdataSource.downloadedCurrentWeather.observeForever { newCurrentData->
            //will persist
            persistFetchedCurrentWearherData(newCurrentData)
        }
    }

    override suspend fun getCurrentWeatherData(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {
       return withContext(Dispatchers.IO){
           initWeatherData()
            return@withContext if(metric) currentWeatherDao.getWeatherMatric()
           else currentWeatherDao.getWeatherImperial()
        }
    }

    private fun persistFetchedCurrentWearherData(fetchedWeather : CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {

            currentWeatherDao.Upsert(fetchedWeather.current)
        }
    }

    private suspend fun initWeatherData(){
        if (isFetchCurrentneeded(ZonedDateTime.now().minusHours(1)))
            fetchCurrentWeather()
    }

    private suspend fun fetchCurrentWeather(){
        currentWeatherdataSource.updateData(
            location = "Delhi",
            language = Locale.getDefault().language
        )
    }

    private fun isFetchCurrentneeded(lastFetchTime : ZonedDateTime): Boolean{
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)

        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}