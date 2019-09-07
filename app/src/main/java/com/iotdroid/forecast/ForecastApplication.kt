package com.iotdroid.forecast

import android.app.Application
import com.iotdroid.forecast.data.WeatherApiService
import com.iotdroid.forecast.data.db.ForecastDatabase
import com.iotdroid.forecast.data.network.ConnectivityInterceptor
import com.iotdroid.forecast.data.network.ConnectivityInterceptorImpl
import com.iotdroid.forecast.data.network.WeatherNetworkDataSource
import com.iotdroid.forecast.data.network.WeatherNetworkDataSourceImpl
import com.iotdroid.forecast.data.repository.ForecastRepository
import com.iotdroid.forecast.data.repository.ForecastRepositoryImpl
import com.iotdroid.forecast.ui.weather.current.CurrentWeatherViewModelprovider
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ForecastApplication : Application(),KodeinAware {
    override val kodein: Kodein = Kodein.lazy {

        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind<ConnectivityInterceptor>()with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from  singleton { WeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(),instance()) }

        bind() from provider { CurrentWeatherViewModelprovider(instance()) }

    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }

}