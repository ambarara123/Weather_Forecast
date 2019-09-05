package com.iotdroid.forecast.data

import com.iotdroid.forecast.data.network.ConnectivityInterceptor
import com.iotdroid.forecast.data.network.ConnectivityInterceptorImpl
import com.iotdroid.forecast.data.network.response.CurrentWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "ff124750c80c4a8a838140545190209"

//http://api-cdn.apixu.com/v1/current.json?key=ff124750c80c4a8a838140545190209&q=London

interface WeatherApiService {

    @GET("current.json")
    fun getCurrentWeatherData(
        @Query("q") location : String,
        @Query("lang") language : String = "en"
    ) : Deferred<CurrentWeatherResponse>

    companion object{
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ) : WeatherApiService{
            val requestIntercepter = Interceptor { chain->
                val url = chain
                    .request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("key", API_KEY)
                    .build()

                val request = chain
                    .request()
                    .newBuilder()
                    .url(url)
                    .build()


                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestIntercepter)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit
                .Builder()
                .client(okHttpClient)
                .baseUrl("http://api-cdn.apixu.com/v1/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApiService::class.java)

        }
    }

}
