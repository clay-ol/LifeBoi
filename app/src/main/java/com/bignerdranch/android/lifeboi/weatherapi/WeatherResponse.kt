package com.bignerdranch.android.lifeboi.weatherapi

import com.bignerdranch.android.lifeboi.CurrentWeatherItem
import com.bignerdranch.android.lifeboi.DailyWeatherItem
import com.bignerdranch.android.lifeboi.HourlyWeatherItem
import com.google.gson.annotations.SerializedName

class WeatherResponse {
//    @SerializedName("current")
    lateinit var lat: String
    lateinit var lon: String
    lateinit var  current: CurrentWeatherItem
    lateinit var hourly: List<HourlyWeatherItem>
    lateinit var daily: List<DailyWeatherItem>


}