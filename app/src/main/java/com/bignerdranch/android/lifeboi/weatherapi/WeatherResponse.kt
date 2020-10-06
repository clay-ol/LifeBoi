package com.bignerdranch.android.lifeboi.weatherapi

import com.bignerdranch.android.lifeboi.WeatherItem
import com.google.gson.annotations.SerializedName

class WeatherResponse {
    @SerializedName("main")
    lateinit var  weatherItems: WeatherItem
}