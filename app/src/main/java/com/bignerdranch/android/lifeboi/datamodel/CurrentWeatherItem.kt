package com.bignerdranch.android.lifeboi.datamodel

data class CurrentWeatherItem (
    var temp: String = "",
    var weather: List<WeatherItem>,
)