package com.bignerdranch.android.lifeboi

data class CurrentWeatherItem (
    var temp: String = "",
    var weather: List<WeatherItem>,
)