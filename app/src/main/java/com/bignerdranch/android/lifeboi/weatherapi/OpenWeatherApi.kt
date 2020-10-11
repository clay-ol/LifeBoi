package com.bignerdranch.android.lifeboi.weatherapi

import com.bignerdranch.android.lifeboi.BuildConfig
import retrofit2.Call
import retrofit2.http.GET


interface OpenWeatherApi{

    @GET( "https://api.openweathermap.org/data/2.5/onecall?" )


    fun fetchWeather(): Call<WeatherResponse>
}