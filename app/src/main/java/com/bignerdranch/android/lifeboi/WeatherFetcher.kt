package com.bignerdranch.android.lifeboi

import retrofit2.Call
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bignerdranch.android.lifeboi.weatherapi.OpenWeatherApi
import com.bignerdranch.android.lifeboi.weatherapi.WeatherResponse
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "WeatherFetcher"

class WeatherFetcher {

    private val openWeatherApi: OpenWeatherApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        openWeatherApi = retrofit.create(OpenWeatherApi::class.java)
    }


    fun fetchWeather(): LiveData<WeatherItem> {
        val responseLiveData: MutableLiveData<WeatherItem> = MutableLiveData()
        val weatherResponse: Call<WeatherResponse> = openWeatherApi.fetchWeather()


        weatherResponse.enqueue(object : Callback<WeatherResponse> {

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch weather", t)
            }

            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                Log.d(TAG, "Response received")
                val weatherResponse: WeatherResponse? = response.body()
                var weatherItems: WeatherItem = weatherResponse!!.weatherItems
                Log.d( TAG, weatherItems.toString() )
                responseLiveData.value = weatherItems
            }
        })
        return responseLiveData
    }
}