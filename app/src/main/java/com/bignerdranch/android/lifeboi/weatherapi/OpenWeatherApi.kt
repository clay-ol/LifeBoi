package com.bignerdranch.android.lifeboi.weatherapi

import retrofit2.Call
import retrofit2.http.GET

interface OpenWeatherApi {

//    @GET( "https://api.openweathermap.org/data/2.5/onecall?"
//            + "lat=" + "42.2626"
//        + "&lon=" + "71.8023"
//        + "&exclude=" + "minutely"
//        + "&appid=" + "bd0d6f55a110a7763eda5a7c0ada5e1e")
    @GET("https://api.openweathermap.org/data/2.5/weather?q=" +
            "Worcester" +
            "&appid=bd0d6f55a110a7763eda5a7c0ada5e1e" +
            "&format=json" +
            "&nojsoncallback=1" +
            "&extras=url_s")
    fun fetchWeather(): Call<WeatherResponse>
}